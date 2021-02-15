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

public class AltaCliente implements WindowListener, ActionListener
{
	Frame frmAltaCliente = new Frame("Alta de Cliente");
	Label lblNombreCliente = new Label("Nombre:");
	TextField txtNombreCliente = new TextField(20);
	Label lblCifCliente = new Label("Cif:");
	TextField txtCifCliente = new TextField(20);
	Button btnAltaCliente = new Button("Alta");
	Button btnCancelarAltaCliente = new Button("Cancelar");

	Dialog dlgConfirmarAltaCliente = new Dialog(frmAltaCliente, "Alta Cliente", true);
	Label lblMensajeAltaCliente = new Label("Alta de Cliente Correcta");
	
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	BaseDatos bd = new BaseDatos();
	
	public AltaCliente()
	{
		frmAltaCliente.setLayout(new FlowLayout());
		frmAltaCliente.add(lblNombreCliente);
		txtNombreCliente.setText("");
		frmAltaCliente.add(txtNombreCliente);
		frmAltaCliente.add(lblCifCliente);
		txtCifCliente.setText("");
		frmAltaCliente.add(txtCifCliente);
		btnAltaCliente.addActionListener(this);
		frmAltaCliente.add(btnAltaCliente);
		btnCancelarAltaCliente.addActionListener(this);
		frmAltaCliente.add(btnCancelarAltaCliente);

		frmAltaCliente.setSize(250,140);
		frmAltaCliente.setResizable(false);
		frmAltaCliente.setLocationRelativeTo(null);
		frmAltaCliente.addWindowListener(this);
		txtNombreCliente.requestFocus();
		frmAltaCliente.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnAltaCliente))
		{
			connection = bd.conectar();
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				if(((txtNombreCliente.getText().length())!=0)
						&&((txtCifCliente.getText().length())!=0))
				{
					sentencia = "INSERT INTO clientes VALUES (null, '"
							+ txtNombreCliente.getText()
							+ "', '" +txtCifCliente.getText() + "')";
					statement.executeUpdate(sentencia);
					lblMensajeAltaCliente.setText("Alta de Cliente Correcta");
				}
				else
				{
					lblMensajeAltaCliente.setText("Faltan datos");
				}
			}
			catch (SQLException sqle)
			{
				lblMensajeAltaCliente.setText("Error en ALTA");
				System.out.println(sqle.getMessage());
			}
			finally
			{
				dlgConfirmarAltaCliente.setLayout(new FlowLayout());
				dlgConfirmarAltaCliente.addWindowListener(this);
				dlgConfirmarAltaCliente.setSize(150,100);
				dlgConfirmarAltaCliente.setResizable(false);
				dlgConfirmarAltaCliente.setLocationRelativeTo(null);
				dlgConfirmarAltaCliente.add(lblMensajeAltaCliente);
				dlgConfirmarAltaCliente.setVisible(true);
			}
		}// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0)
	{
	}

	@Override
	public void windowClosing(WindowEvent arg0)
	{
		if(frmAltaCliente.isActive())
		{
			frmAltaCliente.setVisible(false);
		}
		else if(dlgConfirmarAltaCliente.isActive())
		{
			txtNombreCliente.setText("");
			txtCifCliente.setText("");
			txtNombreCliente.requestFocus();
			dlgConfirmarAltaCliente.setVisible(false);
		}
		
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
}
