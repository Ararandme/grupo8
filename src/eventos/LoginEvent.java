package eventos;

import arreglos.ArregloUsuarios;
import clases.Usuario;
import guis.MenuPrincipal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class LoginEvent {
    String usuario;
    String contrasena;
    JDialog logingDialog;
    ArregloUsuarios arregloUsuarios;
    MenuPrincipal menuPrincipal;

    public LoginEvent(ArregloUsuarios arregloUsuarios, MenuPrincipal principal) {
        this.arregloUsuarios = arregloUsuarios;
        this. menuPrincipal = principal;

    }

    private void logIn() {
        Usuario user = arregloUsuarios.validarUsuario(usuario, contrasena);

        if (user != null) {
            JOptionPane.showMessageDialog(logingDialog, "Bienvenido " + user.getUsuario());

            menuPrincipal.setUserType(user.getUsuario());
            menuPrincipal.setVisible(true);

            logingDialog.dispose();
        } else {
            JOptionPane.showMessageDialog(logingDialog, "Usuario o contrasena incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setButtonEvent(ActionEvent event) {
        logIn();

    }

    public void getKeyIsPressed(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            logIn();
        }
    }


    public LoginEvent setUsuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    public LoginEvent setContrasena(String contrasena) {
        this.contrasena = contrasena;
        return this;
    }

    public LoginEvent setLogingDialog(JDialog logingDialog) {
        this.logingDialog = logingDialog;
        return this;
    }

}
