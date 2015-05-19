package br.com.crud;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class MusicasDAO {
	private Connection connection;

	public MusicasDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}

	public void mostrar() {
		JOptionPane.showMessageDialog(null,
				"Bem-vindo ao sistema de músicas preferidas");
		Musicas m = new Musicas();
		MusicasDAO musica = new MusicasDAO();
		String texto = "1-Inserir\n";
		texto += "2-Listar \n";
		texto += "3-Alterar \n";
		texto += "4-Deletar";
		String escolhaUsuario = JOptionPane.showInputDialog(null, texto);
		int op = Integer.parseInt(escolhaUsuario);

		if (op == 1) {

			JOptionPane.showMessageDialog(null, "Cadastrar música");
			m.setNomeMusica(JOptionPane
					.showInputDialog("Insira o nome da musica"));

			musica.adiciona(m);

		} else if (op == 2) {
			List<Musicas> musicas = musica.getLista();
			String nm = "";
			System.out.println("-----------Músicas-----------");
			for (Musicas musica1 : musicas) {
				nm += musica1.getNomeMusica()+"\n";
				
			}
			JOptionPane.showMessageDialog(null, nm);
		}else if (op == 3){
			JOptionPane.showMessageDialog(null, "Altera");
			m.setId(Integer.parseInt(JOptionPane.showInputDialog("Insira o id da música que será alterada")));
			m.setNomeMusica(JOptionPane.showInputDialog("Insira o novo nome da musica"));
			musica.altera(m);
			
		}else if(op == 4){
			JOptionPane.showMessageDialog(null, "Delete");
			m.setId(Integer.parseInt(JOptionPane.showInputDialog("Insira o id da música que será deletada")));
			
			musica.remove(m);
			
		}

	}

	public void adiciona(Musicas musica) {
		String sql = "insert into Musicas " + "(nomeMusica)" + " values (?)";

		try {

			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, musica.getNomeMusica());

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Musicas> getLista() {
		try {
			List<Musicas> musicas = new ArrayList<Musicas>();
			PreparedStatement stmt = this.connection
					.prepareStatement("select * from musicas");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Musicas musica = new Musicas();
				musica.setId(rs.getInt("id"));
				musica.setNomeMusica(rs.getString("nomeMusica"));

				musicas.add(musica);
			}
			rs.close();
			stmt.close();
			return musicas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void altera(Musicas musica) {
		String sql = "update musicas set nomeMusica=? where id=?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, musica.getNomeMusica());
			stmt.setInt(2, musica.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void remove(Musicas musica) {
		try { //um objeto para enviar instruções SQL para o bando de dados.
			PreparedStatement stmt = connection.prepareStatement("delete"
					+ " from musicas where id=?");
			stmt.setLong(1, musica.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
