package DAO;

import DTO.UsuarioDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class UsuarioDAO {

    Connection conn;

    public ResultSet autenticacaoUsuario(UsuarioDTO objusuariodto) {
        conn = new ConexaoDAO().obtemConexao();

        try {
            String Sql = "Select * from sistema_doadores where login = ? and senha = ?";

            PreparedStatement pstm = conn.prepareStatement(Sql);
            pstm.setString(1, objusuariodto.getLogin());
            pstm.setString(2, objusuariodto.getSenha());

            ResultSet rs = pstm.executeQuery();

            return rs;

        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(null, "O seguinte erro foi encontrado em UsuarioDAO.autenticaçãoUsuario() : " + erro);
            return null;
        }

    }
}
