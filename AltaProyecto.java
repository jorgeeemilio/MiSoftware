package es.studium.MiSoftware;

import java.awt.Button;
import java.awt.Choice;
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

public class AltaProyecto implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Alta Proyecto");
	Label lblNombreProyecto = new Label("Nombre:");
	Label lblFechaInicioProyecto = new Label("Fecha Inicio:");
	Label lblFechaFinProyecto = new Label("Fecha Fin:");
	Label lblClienteProyecto = new Label("Cliente:");
	TextField txtNombreProyecto = new TextField(20);
	TextField txtFechaInicioProyecto = new TextField(20);
	TextField txtFechaFinProyecto = new TextField(20);
	Choice choClienteProyecto = new Choice();
	Button btnAceptarAltaProyecto = new Button("Aceptar");
	Button btnCancelarAltaProyecto = new Button("Cancelar");

	Dialog dlgConfirmarAltaProyecto = new Dialog(ventana, "Alta Proyecto", true);
	Label lblMensajeAltaProyecto = new Label("Alta de Proyecto Correcta");

	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	BaseDatos bd = new BaseDatos();

	public AltaProyecto()
	{
		ventana.setLayout(new FlowLayout());
		ventana.add(lblNombreProyecto);
		ventana.add(txtNombreProyecto);
		ventana.add(lblFechaInicioProyecto);
		ventana.add(txtFechaInicioProyecto);
		ventana.add(lblFechaFinProyecto);
		ventana.add(txtFechaFinProyecto);
		ventana.add(lblClienteProyecto);
		// Conectar BD
		connection = bd.conectar();
		// Obtener todos los Clientes
		// Hacer un SELECT * FROM clientes
		sentencia = "SELECT * FROM clientes";
		try
		{
			//Crear una sentencia
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			choClienteProyecto.removeAll();
			while(rs.next())
			{
				choClienteProyecto.add(rs.getInt("idCliente")
						+"-"+rs.getString("nombreCliente")
						+"-"+rs.getString("cifCliente"));
			}
		}
		catch (SQLException sqle)
		{
		}
		ventana.add(choClienteProyecto);
		btnAceptarAltaProyecto.addActionListener(this);
		ventana.add(btnAceptarAltaProyecto);
		ventana.add(btnCancelarAltaProyecto);

		ventana.setSize(230,300);
		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.addWindowListener(this);
		ventana.setVisible(true);
	}
	public void windowActivated(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowClosing(WindowEvent we)
	{
		ventana.setVisible(false);
	}
	public void windowDeactivated(WindowEvent we) {}
	public void windowDeiconified(WindowEvent we) {}
	public void windowIconified(WindowEvent we) {}
	public void windowOpened(WindowEvent we) {}

	
	@Override
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnAceptarAltaProyecto))
		{
			connection = bd.conectar();
			try
			{
				//Crear una sentencia
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				if(((txtNombreProyecto.getText().length())!=0)
						&&((txtFechaInicioProyecto.getText().length())!=0)
						&&((txtFechaFinProyecto.getText().length())!=0))
				{
					String[] clientes = (choClienteProyecto.getSelectedItem()).split("-");
					// clientes[0] --> idCliente
					// clientes[1] --> nombre
					// clientes[2] --> Cif
					sentencia = "INSERT INTO proyectos VALUES(null, '"
							+txtNombreProyecto.getText()
							+"', '"+txtFechaInicioProyecto.getText()
							+"', '"+txtFechaFinProyecto.getText()
							+"', "+clientes[0]+")";
					statement.executeUpdate(sentencia);
					lblMensajeAltaProyecto.setText("Alta de Proyecto Correcta");
				}
				else
				{
					lblMensajeAltaProyecto.setText("Faltan datos");
				}
			}
			catch (SQLException sqle)
			{
				lblMensajeAltaProyecto.setText("Error en ALTA");
			}
			finally
			{
				dlgConfirmarAltaProyecto.setLayout(new FlowLayout());
				dlgConfirmarAltaProyecto.addWindowListener(this);
				dlgConfirmarAltaProyecto.setSize(150,100);
				dlgConfirmarAltaProyecto.setResizable(false);
				dlgConfirmarAltaProyecto.setLocationRelativeTo(null);
				dlgConfirmarAltaProyecto.add(lblMensajeAltaProyecto);
				dlgConfirmarAltaProyecto.setVisible(true);
			}
		}
	}
}