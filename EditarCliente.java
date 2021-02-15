package es.studium.MiSoftware;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditarCliente implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Edición Cliente");
	Label lblId = new Label("id:");
	Label lblNombre = new Label("Nombre:");
	Label lblCif = new Label("Cif:");
	TextField txtId = new TextField(20);
	TextField txtNombre = new TextField(20);
	TextField txtCif = new TextField(20);
	Button btnAceptar = new Button("Aceptar");
	Button btnCancelar = new Button("Cancelar");
	
	Dialog dlgEditarCliente = new Dialog(ventana, "Editar", true);
	Label lblMensajeEditarCliente = new Label("Modificación correcta");
	
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	BaseDatos bd = new BaseDatos();
	
	public EditarCliente(String elementoEnviado)
	{
		String[] elemento = elementoEnviado.split("-");
		ventana.setLayout(new FlowLayout());
		ventana.add(lblId);
		txtId.setEditable(false);
		txtId.setText(elemento[0]);
		ventana.add(txtId);
		ventana.add(lblNombre);
		txtNombre.setText(elemento[1]);
		ventana.add(txtNombre);
		ventana.add(lblCif);
		txtCif.setText(elemento[2]);
		ventana.add(txtCif);
		btnAceptar.addActionListener(this);
		btnCancelar.addActionListener(this);
		ventana.add(btnAceptar);
		ventana.add(btnCancelar);
		
		ventana.setSize(280,160);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.addWindowListener(this);
		ventana.setVisible(true);
	}

	@Override
	public void windowActivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		ventana.setVisible(false);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnAceptar))
		{
			connection = bd.conectar();
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				if(((txtNombre.getText().length())!=0)
						&&((txtCif.getText().length())!=0))
				{
					sentencia = "UPDATE clientes SET nombreCliente='"
							+ txtNombre.getText()
							+ "', cifCliente='" +txtCif.getText() 
							+ "' WHERE idCliente = "+txtId.getText();
					statement.executeUpdate(sentencia);
					lblMensajeEditarCliente.setText("Modificación de Cliente Correcta");
				}
				else
				{
					lblMensajeEditarCliente.setText("Faltan datos");
				}
			}
			catch (SQLException sqle)
			{
				lblMensajeEditarCliente.setText("Error en Modificación");
			}
			finally
			{
				dlgEditarCliente.setLayout(new FlowLayout());
				dlgEditarCliente.addWindowListener(this);
				dlgEditarCliente.setSize(150,100);
				dlgEditarCliente.setResizable(false);
				dlgEditarCliente.setLocationRelativeTo(null);
				dlgEditarCliente.add(lblMensajeEditarCliente);
				dlgEditarCliente.setVisible(true);
			}
		}
		else
		{
			ventana.setVisible(false);
		}
	}
}
