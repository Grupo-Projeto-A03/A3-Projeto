package CONTROLER.Helper;

import DAO.ConexaoDAO;
import VIEW.AgendarDoacaoTelaView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AgendarHelper {

    private final AgendarDoacaoTelaView helper;

    public AgendarHelper(AgendarDoacaoTelaView helper) {
        this.helper = helper;
    }
    public void buscarinfTabela() throws SQLException {
        DefaultTableModel model = (DefaultTableModel) helper.getTableAgendamento().getModel();
        model.setRowCount(0);

        String sql = "SELECT * FROM tb_agendamento";

        try (Connection conn = ConexaoDAO.obtemConexao(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                String[] rowData = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getString(i);
                }
                model.addRow(rowData);
            }
        }
    }

    public int inserirAgendamento() throws Exception {
        String id_doador = helper.getTxtID().getText();
        String hospital = (String) helper.getListaHospital().getSelectedItem();
        String data = helper.getTxtData().getText();
        String hora = helper.getTxtHora().getText();
        String observacao = helper.getTxtareaObservacao().getText();
        String sql = "INSERT INTO tb_agendamento (hospital, data_doacao, hora_doacao, observacao, doador_id) "
                + "VALUES (?,?,?,?,?)";

        try (Connection conn = ConexaoDAO.obtemConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hospital);
            ps.setString(2, data);
            ps.setString(3, hora);
            ps.setString(4, observacao);
            ps.setString(5, id_doador);

            return ps.executeUpdate();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro);
        }
        return 0;
    }
}
