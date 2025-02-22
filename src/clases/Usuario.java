package clases;

public class Usuario {

	private String usuario;
	private String contrasena;
	private String tipo;

	public Usuario(String usuario, String contrasena, String tipo) {
		this.usuario = usuario;
		this.contrasena = contrasena;
		this.tipo = tipo;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public String getTipo() {
		return tipo;
	}

}
