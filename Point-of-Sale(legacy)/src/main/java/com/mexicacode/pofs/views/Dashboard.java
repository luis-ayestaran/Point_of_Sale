package com.mexicacode.pofs.views;

import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.mexicacode.pofs.configuration.SystemMessages;

import com.mexicacode.pofs.exceptions.ToolkitException;

import com.mexicacode.pofs.app.PointOfSale;
import com.mexicacode.pofs.entities.SaleDetail;
import com.mexicacode.pofs.entities.Product;
import com.mexicacode.pofs.entities.Sale;
import com.mexicacode.pofs.entities.User;
import com.mexicacode.pofs.printer.printer;
import com.mexicacode.pofs.entities.ProductType;

import com.mexicacode.pofs.services.AuthenticationService;
import com.mexicacode.pofs.services.FlagService;
//import com.mexicacode.pofs.services.FlagService;
//import com.mexicacode.pofs.services.TypeService;
import com.mexicacode.pofs.services.WarehouseService;
import com.mexicacode.pofs.services.TimeService;
import com.mexicacode.pofs.services.SaleService;
import com.mexicacode.pofs.services.ScaleService;
//import com.mexicacode.pofs.services.TimeService;

import com.mexicacode.pofs.utils.Toolkit;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JCheckBox;

public class Dashboard extends JFrame{
	//private FlagService flag;
	private WarehouseService ws;
	
	private JTable tablaVentas;
	private JTable tablaProductos;
	private JTable tablaCategorias;
	
	private JTextField txtBuscar;
	private JTextField txtSubtotal;
	private JTextField txtDescuento;
	private JTextField txtTotal;
	private JTextField txtCodigoBar;
	private JTextField txtCantidad;
	private JTextField txtNomProduct_1;
	private JTextField txtNomProduct_2;
	private JTextField txtCodBar_1;
	private JTextField txtMarca_1;
	private JTextField txtMarca_2;
	private JTextField txtCategoria_1;
	private JTextField txtCategoria_2;
	private JTextField txtPrecioVenta_1;
	private JTextField txtPrecioVenta_2;
	private JTextField txtMinStock_1;
	private JTextField txtMinStock_2;
	private JTextField txtMaxStock_1;
	private JTextField txtMaxStock_2;
	private JTextField txtFechaEntrada_1;
	private JTextField txtFechaEntrada_2;
	private JTextField txtCostoCompra_1;
	private JTextField txtCostoCompra_2;
	private JTextField txtUnidad_1;
	private JTextField txtUnidad_2;
	private JTextField txtFechaCaducidad_1;
	private JTextField txtFechaCaducidad_2;
	private JTextField txtCantidad_1;
	private JTextField txtCantidad_2;
	private JTextField txtCategory;
	private JTextField txtCategory_1;
	private JTextField txtBuscarCategoria;
	
	private JCheckBox chckbxAGranel;
	
	private Integer id;
	
	private Float cantidad;
	
	private Double subtotal;
	private Double descuento;
	private Double total;
	
	private List<Product> listaVenta;
	private List<Type> listaCategoria;
	
	private Sale venta;
	private SaleService ss;
	
	private Product producto;
	
	private JTextField txtBuscarProducto;
	private JTextField textField;
	
	private FlagService flag;
	private ScaleService scale;
	
	public Dashboard(String name) {
		this.id = 0;
		this.cantidad = 0.0F;
		this.subtotal = 0.0;
		this.descuento = 0.0;
		this.total = 0.0;
		this.venta = null;
		this.producto = null;
		this.flag = new FlagService();
		this.scale = new ScaleService();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(Dashboard.class.getResource("/com/mexicacode/pofs/images/LOGO.png")));
		try {
			this.setTitle(SystemMessages.getMessage("system.appname"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	    this.setSize(screenSize.width, screenSize.height - 45);
		SpringLayout springLayout = new SpringLayout();
		this.getContentPane().setLayout(springLayout);
		
		final JPanel background = new JPanel();
		background.setBackground(SystemColor.menu);
		springLayout.putConstraint(SpringLayout.NORTH, background, 0, SpringLayout.NORTH, this.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, background, 0, SpringLayout.WEST, this.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, background, 0, SpringLayout.SOUTH, this.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, background, 0, SpringLayout.EAST, this.getContentPane());
		this.getContentPane().add(background);
		final SpringLayout sl_background = new SpringLayout();
		background.setLayout(sl_background);
		
		//Panel de dashboard. Contiene el menú de las acciones que el usuario puede realizar.--------------------------------------------------------
		final JPanel menuPanel = new JPanel();
		sl_background.putConstraint(SpringLayout.NORTH, menuPanel, 0, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.WEST, menuPanel, 0, SpringLayout.WEST, background);
		sl_background.putConstraint(SpringLayout.SOUTH, menuPanel, 0, SpringLayout.SOUTH, background);
		sl_background.putConstraint(SpringLayout.EAST, menuPanel, 150, SpringLayout.WEST, background);
		menuPanel.setBackground(new Color(51, 51, 51));
		background.add(menuPanel);
		SpringLayout sl_menuPanel = new SpringLayout();
		menuPanel.setLayout(sl_menuPanel);
		
		//Panel de búsqueda y configuraciones.
		JPanel userPanel = new JPanel();
		sl_background.putConstraint(SpringLayout.NORTH, userPanel, 0, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.WEST, userPanel, 0, SpringLayout.EAST, menuPanel);
		sl_background.putConstraint(SpringLayout.SOUTH, userPanel, 85, SpringLayout.NORTH, background);
		sl_background.putConstraint(SpringLayout.EAST, userPanel, 0, SpringLayout.EAST, background);
		userPanel.setBackground(new Color(255, 255, 255));
		background.add(userPanel);
		SpringLayout sl_userPanel = new SpringLayout();
		userPanel.setLayout(sl_userPanel);
		
		//Panel del logo del negocio
		JPanel LogoPanel = new JPanel();
		LogoPanel.setBackground(new Color(60, 179, 113));
		sl_menuPanel.putConstraint(SpringLayout.SOUTH, LogoPanel, 85, SpringLayout.NORTH, menuPanel);
		sl_menuPanel.putConstraint(SpringLayout.NORTH, LogoPanel, 0, SpringLayout.NORTH, menuPanel);
		sl_menuPanel.putConstraint(SpringLayout.WEST, LogoPanel, 0, SpringLayout.WEST, menuPanel);
		sl_menuPanel.putConstraint(SpringLayout.EAST, LogoPanel, 150, SpringLayout.WEST, menuPanel);
		menuPanel.add(LogoPanel);
		SpringLayout sl_LogoPanel = new SpringLayout();
		LogoPanel.setLayout(sl_LogoPanel);
		
		//Panel principal. Aquí se muestran los paneles de cada una de las opciones que puede escoger el usuario en el dashboard---------------------
		final JPanel mainPanel = new JPanel(new CardLayout());
		sl_background.putConstraint(SpringLayout.NORTH, mainPanel, 0, SpringLayout.SOUTH, userPanel);
		sl_background.putConstraint(SpringLayout.WEST, mainPanel, 0, SpringLayout.EAST, menuPanel);
		sl_background.putConstraint(SpringLayout.EAST, mainPanel, 0, SpringLayout.EAST, background);
		sl_background.putConstraint(SpringLayout.SOUTH, mainPanel, 0, SpringLayout.SOUTH, background);
		background.add(mainPanel);
		
		//Panel introductorio. Muestra un resumen de las estadísticas de venta y notificaciones pendientes.------------------------------------------
		final JPanel inicioPanel = new JPanel();
		inicioPanel.setBackground(new Color(225, 225, 225));
		sl_background.putConstraint(SpringLayout.NORTH, inicioPanel, 0, SpringLayout.NORTH, mainPanel);
		sl_background.putConstraint(SpringLayout.WEST, inicioPanel, 0, SpringLayout.EAST, mainPanel);
		sl_background.putConstraint(SpringLayout.EAST, inicioPanel, 0, SpringLayout.EAST, mainPanel);
		sl_background.putConstraint(SpringLayout.SOUTH, inicioPanel, 0, SpringLayout.SOUTH, mainPanel);
		mainPanel.add(inicioPanel);
		SpringLayout sl_inicioPanel = new SpringLayout();
		inicioPanel.setLayout(sl_inicioPanel);
		
		JLabel lblBienvenido = new JLabel("Bienvenido");
		sl_inicioPanel.putConstraint(SpringLayout.NORTH, lblBienvenido, 30, SpringLayout.NORTH, inicioPanel);
		sl_inicioPanel.putConstraint(SpringLayout.WEST, lblBienvenido, 50, SpringLayout.WEST, inicioPanel);
		lblBienvenido.setFont(new Font("SF Pro Display", Font.PLAIN, 45));
		inicioPanel.add(lblBienvenido);
		
		JPanel saludoPanel = new JPanel();
		sl_inicioPanel.putConstraint(SpringLayout.WEST, saludoPanel, 0, SpringLayout.WEST, lblBienvenido);
		sl_inicioPanel.putConstraint(SpringLayout.EAST, saludoPanel, -36, SpringLayout.EAST, inicioPanel);
		saludoPanel.setBackground(Color.WHITE);
		sl_inicioPanel.putConstraint(SpringLayout.NORTH, saludoPanel, 10, SpringLayout.SOUTH, lblBienvenido);
		sl_inicioPanel.putConstraint(SpringLayout.SOUTH, saludoPanel, -50, SpringLayout.SOUTH, inicioPanel);
		inicioPanel.add(saludoPanel);
		SpringLayout sl_saludoPanel = new SpringLayout();
		saludoPanel.setLayout(sl_saludoPanel);
		
		JLabel lblBienvenidoDeNuevo = new JLabel("Todo bien por aqu\u00ED. Veamos un resumen de tus finanzas.");
		sl_saludoPanel.putConstraint(SpringLayout.WEST, lblBienvenidoDeNuevo, 20, SpringLayout.WEST, saludoPanel);
		sl_saludoPanel.putConstraint(SpringLayout.NORTH, lblBienvenidoDeNuevo, 20, SpringLayout.NORTH, saludoPanel);
		lblBienvenidoDeNuevo.setForeground(new Color(102, 102, 102));
		lblBienvenidoDeNuevo.setFont(new Font("SF Pro Display", Font.PLAIN, 20));
		saludoPanel.add(lblBienvenidoDeNuevo);
		
		JLabel lblRecordatorios = new JLabel("Recordatorios");
		lblRecordatorios.setForeground(new Color(102, 102, 102));
		sl_saludoPanel.putConstraint(SpringLayout.NORTH, lblRecordatorios, 0, SpringLayout.NORTH, lblBienvenidoDeNuevo);
		sl_saludoPanel.putConstraint(SpringLayout.WEST, lblRecordatorios, 0, SpringLayout.WEST, saludoPanel);
		lblRecordatorios.setFont(new Font("SF Pro Display", Font.PLAIN, 30));
		saludoPanel.add(lblRecordatorios);
		
		JSeparator sepRecordatorios = new JSeparator();
		sepRecordatorios.setForeground(new Color(204, 204, 204));
		sepRecordatorios.setOrientation(SwingConstants.VERTICAL);
		sl_saludoPanel.putConstraint(SpringLayout.EAST, sepRecordatorios, -30, SpringLayout.WEST, lblRecordatorios);
		sl_saludoPanel.putConstraint(SpringLayout.NORTH, sepRecordatorios, 20, SpringLayout.NORTH, saludoPanel);
		sl_saludoPanel.putConstraint(SpringLayout.SOUTH, sepRecordatorios, -20, SpringLayout.SOUTH, saludoPanel);
		sepRecordatorios.setBackground(new Color(247, 247, 247));
		saludoPanel.add(sepRecordatorios);
		
		JLabel lblTodoEnOrden = new JLabel("No tienes notificaciones nuevas.");
		sl_saludoPanel.putConstraint(SpringLayout.NORTH, lblTodoEnOrden, 67, SpringLayout.NORTH, saludoPanel);
		sl_saludoPanel.putConstraint(SpringLayout.WEST, lblTodoEnOrden, 705, SpringLayout.WEST, saludoPanel);
		sl_saludoPanel.putConstraint(SpringLayout.WEST, lblRecordatorios, 0, SpringLayout.WEST, lblTodoEnOrden);
		sl_saludoPanel.putConstraint(SpringLayout.SOUTH, lblRecordatorios, -6, SpringLayout.NORTH, lblTodoEnOrden);
		lblTodoEnOrden.setForeground(new Color(102, 102, 102));
		lblTodoEnOrden.setFont(new Font("SF Pro Display", Font.PLAIN, 18));
		saludoPanel.add(lblTodoEnOrden);
		
		//Panel de productos. Muestra opciones a realizar con el inventario del negocio.-------------------------------------------------------------
		final JPanel productosPanel = new JPanel();
		productosPanel.setBackground(new Color(225, 225, 225));
		sl_background.putConstraint(SpringLayout.NORTH, productosPanel, 0, SpringLayout.NORTH, mainPanel);
		sl_background.putConstraint(SpringLayout.WEST, productosPanel, 0, SpringLayout.EAST, mainPanel);
		sl_background.putConstraint(SpringLayout.EAST, productosPanel, 0, SpringLayout.EAST, mainPanel);
		sl_background.putConstraint(SpringLayout.SOUTH, productosPanel, 0, SpringLayout.SOUTH, mainPanel);
		mainPanel.add(productosPanel);
		SpringLayout sl_productosPanel = new SpringLayout();
		productosPanel.setLayout(sl_productosPanel);
		
		JLabel lblProductos_1 = new JLabel("Productos");
		lblProductos_1.setFont(new Font("SF Pro Display", Font.PLAIN, 45));
		sl_productosPanel.putConstraint(SpringLayout.NORTH, lblProductos_1, 30, SpringLayout.NORTH, productosPanel);
		sl_productosPanel.putConstraint(SpringLayout.WEST, lblProductos_1, 50, SpringLayout.WEST, productosPanel);
		productosPanel.add(lblProductos_1);
		
		//CardLayout que nos permite cambiar entre páneles de la sección productos
		final CardLayout card = new CardLayout();
		//Aplicamos el CardLayout al panel contenedor menuProductosPanel
		final JPanel menuProductosPanel = new JPanel(card);
		menuProductosPanel.setBorder(null);
		menuProductosPanel.setBackground(Color.WHITE);
		sl_productosPanel.putConstraint(SpringLayout.NORTH, menuProductosPanel, 10, SpringLayout.SOUTH, lblProductos_1);
		sl_productosPanel.putConstraint(SpringLayout.WEST, menuProductosPanel, 50, SpringLayout.WEST, productosPanel);
		sl_productosPanel.putConstraint(SpringLayout.EAST, menuProductosPanel, -50, SpringLayout.EAST, productosPanel);
		sl_productosPanel.putConstraint(SpringLayout.SOUTH, menuProductosPanel, -50, SpringLayout.SOUTH, productosPanel);
		productosPanel.add(menuProductosPanel);
		
		//-----------Añadimos los paneles que cambiarán dinámicamente dentro del contenedor-----------
		//Panel de opciones de visualización del inventario
		final JPanel opcionesPanel = new JPanel();
		opcionesPanel.setBorder(null);
		opcionesPanel.setBackground(new Color(255, 255, 255));
		SpringLayout sl_opcionesPanel = new SpringLayout();
		opcionesPanel.setLayout(sl_opcionesPanel);
		menuProductosPanel.add(opcionesPanel, "1");
		
		//Panel que muestra la lista de productos en el inventario
		final JPanel listaProductosPanel = new JPanel();
		listaProductosPanel.setBorder(null);
		listaProductosPanel.setBackground(new Color(255, 255, 255));
		final SpringLayout sl_listaProductosPanel = new SpringLayout();
		listaProductosPanel.setLayout(sl_listaProductosPanel);
		menuProductosPanel.add(listaProductosPanel, "2");

		//Panel que muestra las categorías registradas
		final JPanel listaCategoriasPanel = new JPanel();
		listaCategoriasPanel.setBorder(null);
		listaCategoriasPanel.setBackground(new Color(255, 255, 255));
		final SpringLayout sl_listaCategoriasPanel = new SpringLayout();
		listaCategoriasPanel.setLayout(sl_listaCategoriasPanel);
		menuProductosPanel.add(listaCategoriasPanel, "3");
		
		//------(AÚN NO IMPLEMENTADO)------- Panel que muestra un recuento de las próximas adquisiciones que realizará el negocio 
		final JPanel proxComprasPanel = new JPanel();
		proxComprasPanel.setBorder(null);
		proxComprasPanel.setBackground(new Color(255, 255, 255));
		SpringLayout sl_proxComprasPanel = new SpringLayout();
		proxComprasPanel.setLayout(sl_proxComprasPanel);
		menuProductosPanel.add(proxComprasPanel, "4");
		
		//Panel con campos para agregar productos
		final JPanel agregaProductoPanel = new JPanel();
		agregaProductoPanel.setBorder(null);
		agregaProductoPanel.setBackground(new Color(255, 255, 255));
		SpringLayout sl_agregaProductoPanel = new SpringLayout();
		agregaProductoPanel.setLayout(sl_agregaProductoPanel);
		menuProductosPanel.add(agregaProductoPanel, "5");
		
		//Panel con campos para editar productos
		final JPanel editaProductoPanel = new JPanel();
		editaProductoPanel.setBorder(null);
		editaProductoPanel.setBackground(new Color(255, 255, 255));
		SpringLayout sl_editaProductoPanel = new SpringLayout();
		editaProductoPanel.setLayout(sl_editaProductoPanel);
		menuProductosPanel.add(editaProductoPanel, "6");
		
		//------------------------Componentes del panel edición de productos------------------------------------
		
		final JLabel lblBack_5 = new JLabel("");
		lblBack_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblBack_5.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/back(w).png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblBack_5.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/back.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				lblBack_5.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/back.png")));
				card.show(menuProductosPanel, "2");
			}
		});
		lblBack_5.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/back.png")));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, lblBack_5, 0, SpringLayout.NORTH, editaProductoPanel);
		sl_editaProductoPanel.putConstraint(SpringLayout.SOUTH, lblBack_5, 40, SpringLayout.NORTH, editaProductoPanel);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, lblBack_5, 0, SpringLayout.WEST, editaProductoPanel);
		sl_editaProductoPanel.putConstraint(SpringLayout.EAST, lblBack_5, 60, SpringLayout.WEST, editaProductoPanel);
		editaProductoPanel.add(lblBack_5);
		
		JLabel lblPropDatos_2 = new JLabel("Proporciona el c\u00F3digo de barras o el nombre del producto que quieres editar.");
		lblPropDatos_2.setFont(new Font("SF Pro Display", Font.PLAIN, 18));
		lblPropDatos_2.setForeground(new Color(102, 102, 102));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, lblPropDatos_2, 10, SpringLayout.SOUTH, lblBack_5);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, lblPropDatos_2, 50, SpringLayout.WEST, editaProductoPanel);
		editaProductoPanel.add(lblPropDatos_2);
		
		final JLabel lblNomProduct_2 = new JLabel("C\u00F3digo de barras / nombre");
		lblNomProduct_2.setForeground(new Color(102, 102, 102));
		lblNomProduct_2.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, lblNomProduct_2, 60, SpringLayout.SOUTH, lblBack_5);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, lblNomProduct_2, 50, SpringLayout.WEST, editaProductoPanel);
		editaProductoPanel.add(lblNomProduct_2);
		
		txtNomProduct_2 = new JTextField();
		txtNomProduct_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!txtNomProduct_2.getText().equals(""))
				{
					Product p = searchProduct(txtNomProduct_2.getText());
					if(p != null)
					{
						producto = p;
						System.out.println(producto);
						showEditProductPanel(p);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "No se encontraron coincidencias", "Producto no encontrado", JOptionPane.ERROR_MESSAGE);
						txtNomProduct_2.setText("");
					}
				}
			}
		});
		txtNomProduct_2.setBackground(new Color(225, 225, 225));
		txtNomProduct_2.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		txtNomProduct_2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, txtNomProduct_2, -2, SpringLayout.NORTH, lblNomProduct_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, txtNomProduct_2, 15, SpringLayout.EAST, lblNomProduct_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.SOUTH, txtNomProduct_2, 2, SpringLayout.SOUTH, lblNomProduct_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.EAST, txtNomProduct_2, 265, SpringLayout.EAST, lblNomProduct_2);
		editaProductoPanel.add(txtNomProduct_2);
		txtNomProduct_2.setColumns(10);
		
		JLabel lblMarca_2 = new JLabel("Marca");
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, lblMarca_2, 25, SpringLayout.SOUTH, lblNomProduct_2);
		lblMarca_2.setForeground(new Color(100, 100, 100));
		lblMarca_2.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, lblMarca_2, 0, SpringLayout.WEST, lblNomProduct_2);
		editaProductoPanel.add(lblMarca_2);
		
		txtMarca_2 = new JTextField();
		txtMarca_2.setBackground(new Color(225, 225, 225));
		txtMarca_2.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		txtMarca_2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, txtMarca_2, 0, SpringLayout.WEST, txtNomProduct_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.EAST, txtMarca_2, 0, SpringLayout.EAST, txtNomProduct_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.SOUTH, txtMarca_2, 2, SpringLayout.SOUTH, lblMarca_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, txtMarca_2, -2, SpringLayout.NORTH, lblMarca_2);
		editaProductoPanel.add(txtMarca_2);
		txtMarca_2.setColumns(10);
		txtMarca_2.setVisible(false);
		
		JLabel lblCategoria_2 = new JLabel("Categor\u00EDa");
		lblCategoria_2.setForeground(new Color(100, 100, 100));
		lblCategoria_2.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, lblCategoria_2, 25, SpringLayout.SOUTH, lblMarca_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, lblCategoria_2, 0, SpringLayout.WEST, lblNomProduct_2);
		editaProductoPanel.add(lblCategoria_2);
		
		txtCategoria_2 = new JTextField();
		txtCategoria_2.setBackground(new Color(225, 225, 225));
		txtCategoria_2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtCategoria_2.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, txtCategoria_2, 0, SpringLayout.WEST, txtNomProduct_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.SOUTH, txtCategoria_2, 2, SpringLayout.SOUTH, lblCategoria_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, txtCategoria_2, -2, SpringLayout.NORTH, lblCategoria_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.EAST, txtCategoria_2, 0, SpringLayout.EAST, txtNomProduct_2);
		editaProductoPanel.add(txtCategoria_2);
		txtCategoria_2.setColumns(10);
		txtCategoria_2.setVisible(false);
		
		JLabel lblCantidad_2 = new JLabel("Cantidad");
		lblCantidad_2.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		lblCantidad_2.setForeground(new Color(100, 100, 100));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, lblCantidad_2, 25, SpringLayout.SOUTH, lblCategoria_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, lblCantidad_2, 0, SpringLayout.WEST, lblNomProduct_2);
		editaProductoPanel.add(lblCantidad_2);
		
		txtCantidad_2 = new JTextField();
		txtCantidad_2.setBackground(new Color(225, 225, 225));
		txtCantidad_2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtCantidad_2.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, txtCantidad_2, -2, SpringLayout.NORTH, lblCantidad_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.SOUTH, txtCantidad_2, 2, SpringLayout.SOUTH, lblCantidad_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, txtCantidad_2, 0, SpringLayout.WEST, txtNomProduct_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.EAST, txtCantidad_2, 0, SpringLayout.EAST, txtNomProduct_2);
		editaProductoPanel.add(txtCantidad_2);
		txtCantidad_2.setColumns(10);
		txtCantidad_2.setVisible(false);
		
		JLabel lblUnidad_2 = new JLabel("Unidad de medida");
		lblUnidad_2.setForeground(new Color(100, 100, 100));
		lblUnidad_2.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, lblUnidad_2, 25, SpringLayout.SOUTH, lblCantidad_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, lblUnidad_2, 0, SpringLayout.WEST, lblNomProduct_2);
		editaProductoPanel.add(lblUnidad_2);
		
		txtUnidad_2 = new JTextField();
		txtUnidad_2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtUnidad_2.setBackground(new Color(225, 225, 225));
		txtUnidad_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(txtUnidad_2.getText().equals("Piezas, cajas, KG,..."))
				{
					txtUnidad_2.setText("");
					txtUnidad_2.setForeground(new Color(0, 0, 0));
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtUnidad_2.getText().equals(""))
				{
					txtUnidad_2.setText("Piezas, cajas, KG,...");
					txtUnidad_2.setForeground(new Color(140, 140, 140));
				}
			}
		});
		txtUnidad_2.setForeground(new Color(0, 0, 0));
		txtUnidad_2.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		txtUnidad_2.setText("Piezas, cajas, KG,...");
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, txtUnidad_2, -2, SpringLayout.NORTH, lblUnidad_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.SOUTH, txtUnidad_2, 2, SpringLayout.SOUTH, lblUnidad_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, txtUnidad_2, 0, SpringLayout.WEST, txtNomProduct_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.EAST, txtUnidad_2, 0, SpringLayout.EAST, txtNomProduct_2);
		editaProductoPanel.add(txtUnidad_2);
		txtUnidad_2.setColumns(10);
		txtUnidad_2.setVisible(false);
		
		JLabel lblCostoCompra_2 = new JLabel("Costo (Unitario) - Compra");
		lblCostoCompra_2.setForeground(new Color(100, 100, 100));
		lblCostoCompra_2.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, lblCostoCompra_2, 0, SpringLayout.NORTH, lblNomProduct_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, lblCostoCompra_2, 100, SpringLayout.EAST, txtNomProduct_2);
		editaProductoPanel.add(lblCostoCompra_2);
		
		txtCostoCompra_2 = new JTextField();
		txtCostoCompra_2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtCostoCompra_2.setBackground(new Color(225, 225, 225));
		txtCostoCompra_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtCostoCompra_2.getText().equals("0.00")) {
					txtCostoCompra_2.setForeground(new Color(0, 0, 0));
					txtCostoCompra_2.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtCostoCompra_2.getText().equals("")) {
					txtCostoCompra_2.setForeground(new Color(140, 140, 140));
					txtCostoCompra_2.setText("0.00");
				}
			}
		});
		txtCostoCompra_2.setText("0.00");
		txtCostoCompra_2.setForeground(new Color(0, 0, 0));
		txtCostoCompra_2.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, txtCostoCompra_2, -2, SpringLayout.NORTH, lblCostoCompra_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.SOUTH, txtCostoCompra_2, 2, SpringLayout.SOUTH, lblCostoCompra_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, txtCostoCompra_2, 40, SpringLayout.EAST, lblCostoCompra_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.EAST, txtCostoCompra_2, 280, SpringLayout.EAST, lblCostoCompra_2);
		editaProductoPanel.add(txtCostoCompra_2);
		txtCostoCompra_2.setColumns(10);
		txtCostoCompra_2.setVisible(false);
		
		JLabel lblCostoCompraIcon_2 = new JLabel("$");
		lblCostoCompraIcon_2.setForeground(new Color(100, 100, 100));
		lblCostoCompraIcon_2.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_editaProductoPanel.putConstraint(SpringLayout.SOUTH, lblCostoCompraIcon_2, 0, SpringLayout.SOUTH, lblCostoCompra_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, lblCostoCompraIcon_2, 0, SpringLayout.NORTH, lblCostoCompra_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.EAST, lblCostoCompraIcon_2, -5, SpringLayout.WEST, txtCostoCompra_2);
		editaProductoPanel.add(lblCostoCompraIcon_2);
		
		JLabel lblPrecioVenta_2 = new JLabel("Precio (Unitario) - Venta");
		lblPrecioVenta_2.setForeground(new Color(100, 100, 100));
		lblPrecioVenta_2.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, lblPrecioVenta_2, 0, SpringLayout.NORTH, lblMarca_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, lblPrecioVenta_2, 0, SpringLayout.WEST, lblCostoCompra_2);
		editaProductoPanel.add(lblPrecioVenta_2);
		
		txtPrecioVenta_2 = new JTextField();
		txtPrecioVenta_2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtPrecioVenta_2.setBackground(new Color(225, 225, 225));
		txtPrecioVenta_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtPrecioVenta_2.getText().equals("0.00")) {
					txtPrecioVenta_2.setForeground(new Color(0, 0, 0));
					txtPrecioVenta_2.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtPrecioVenta_2.getText().equals("")) {
					txtPrecioVenta_2.setForeground(new Color(140, 140, 140));
					txtPrecioVenta_2.setText("0.00");
				}
			}
		});
		txtPrecioVenta_2.setForeground(new Color(0, 0, 0));
		txtPrecioVenta_2.setText("0.00");
		txtPrecioVenta_2.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, txtPrecioVenta_2, -2, SpringLayout.NORTH, lblPrecioVenta_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.SOUTH, txtPrecioVenta_2, 2, SpringLayout.SOUTH, lblPrecioVenta_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, txtPrecioVenta_2, 0, SpringLayout.WEST, txtCostoCompra_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.EAST, txtPrecioVenta_2, 0, SpringLayout.EAST, txtCostoCompra_2);
		editaProductoPanel.add(txtPrecioVenta_2);
		txtPrecioVenta_2.setColumns(10);
		txtPrecioVenta_2.setVisible(false);
		
		JLabel lblPrecioVentaIcon_2 = new JLabel("$");
		lblPrecioVentaIcon_2.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		lblPrecioVentaIcon_2.setForeground(new Color(100, 100, 100));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, lblPrecioVentaIcon_2, 0, SpringLayout.NORTH, lblPrecioVenta_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.EAST, lblPrecioVentaIcon_2, 0, SpringLayout.EAST, lblCostoCompraIcon_2);
		editaProductoPanel.add(lblPrecioVentaIcon_2);
		
		JLabel lblMinStock_2 = new JLabel("M\u00EDnimo Stock");
		lblMinStock_2.setForeground(new Color(100, 100, 100));
		lblMinStock_2.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, lblMinStock_2, 0, SpringLayout.NORTH, lblCategoria_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, lblMinStock_2, 0, SpringLayout.WEST, lblPrecioVenta_2);
		editaProductoPanel.add(lblMinStock_2);
		
		txtMinStock_2 = new JTextField();
		txtMinStock_2.setForeground(new Color(0, 0, 0));
		txtMinStock_2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtMinStock_2.setBackground(new Color(225, 225, 225));
		txtMinStock_2.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, txtMinStock_2, -2, SpringLayout.NORTH, lblMinStock_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.SOUTH, txtMinStock_2, 2, SpringLayout.SOUTH, lblMinStock_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, txtMinStock_2, 0, SpringLayout.WEST, txtPrecioVenta_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.EAST, txtMinStock_2, 0, SpringLayout.EAST, txtPrecioVenta_2);
		editaProductoPanel.add(txtMinStock_2);
		txtMinStock_2.setColumns(10);
		txtMinStock_2.setVisible(false);
		
		JLabel lblMaxStock_2 = new JLabel("M\u00E1ximo Stock");
		lblMaxStock_2.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		lblMaxStock_2.setForeground(new Color(100, 100, 100));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, lblMaxStock_2, 0, SpringLayout.NORTH, lblCantidad_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, lblMaxStock_2, 0, SpringLayout.WEST, lblPrecioVenta_2);
		editaProductoPanel.add(lblMaxStock_2);
		
		txtMaxStock_2 = new JTextField();
		txtMaxStock_2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtMaxStock_2.setBackground(new Color(225, 225, 225));
		txtMaxStock_2.setForeground(new Color(0, 0, 0));
		txtMaxStock_2.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, txtMaxStock_2, -2, SpringLayout.NORTH, lblMaxStock_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.SOUTH, txtMaxStock_2, 2, SpringLayout.SOUTH, lblMaxStock_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, txtMaxStock_2, 0, SpringLayout.WEST, txtPrecioVenta_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.EAST, txtMaxStock_2, 0, SpringLayout.EAST, txtPrecioVenta_2);
		editaProductoPanel.add(txtMaxStock_2);
		txtMaxStock_2.setColumns(10);
		txtMaxStock_2.setVisible(false);
		
		JLabel lblFechaEntrada_2 = new JLabel("Fecha de entrada");
		lblFechaEntrada_2.setForeground(new Color(100, 100, 100));
		lblFechaEntrada_2.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, lblFechaEntrada_2, 0, SpringLayout.NORTH, lblUnidad_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, lblFechaEntrada_2, 0, SpringLayout.WEST, lblPrecioVenta_2);
		editaProductoPanel.add(lblFechaEntrada_2);
		
		txtFechaEntrada_2 = new JTextField();
		txtFechaEntrada_2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtFechaEntrada_2.setBackground(new Color(225, 225, 225));
		txtFechaEntrada_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtFechaEntrada_2.getText().equals("AAAA-MM-DD"))
				{
					txtFechaEntrada_2.setForeground(new Color(0, 0, 0));
					txtFechaEntrada_2.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtFechaEntrada_2.getText().equals(""))
				{
					txtFechaEntrada_2.setForeground(new Color(140, 140, 140));
					txtFechaEntrada_2.setText("AAAA-MM-DD");
				}
			}
		});
		txtFechaEntrada_2.setForeground(new Color(0, 0, 0));
		txtFechaEntrada_2.setText("AAAA-MM-DD");
		txtFechaEntrada_2.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, txtFechaEntrada_2, -2, SpringLayout.NORTH, lblFechaEntrada_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.SOUTH, txtFechaEntrada_2, 2, SpringLayout.SOUTH, lblFechaEntrada_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, txtFechaEntrada_2, 0, SpringLayout.WEST, txtPrecioVenta_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.EAST, txtFechaEntrada_2, 0, SpringLayout.EAST, txtPrecioVenta_2);
		editaProductoPanel.add(txtFechaEntrada_2);
		txtFechaEntrada_2.setColumns(10);
		txtFechaEntrada_2.setVisible(false);
		
		JLabel lblFechaCaducidad_2 = new JLabel("Fecha de caducidad");
		lblFechaCaducidad_2.setForeground(new Color(100, 100, 100));
		lblFechaCaducidad_2.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, lblFechaCaducidad_2, 25, SpringLayout.SOUTH, lblFechaEntrada_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, lblFechaCaducidad_2, 0, SpringLayout.WEST, lblCostoCompra_2);
		editaProductoPanel.add(lblFechaCaducidad_2);
		
		txtFechaCaducidad_2 = new JTextField();
		txtFechaCaducidad_2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtFechaCaducidad_2.setBackground(new Color(225, 225, 225));
		txtFechaCaducidad_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtFechaCaducidad_2.getText().equals("AAAA-MM-DD")) {
					txtFechaCaducidad_2.setForeground(new Color(0, 0, 0));
					txtFechaCaducidad_2.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtFechaCaducidad_2.getText().equals("")) {
					txtFechaCaducidad_2.setForeground(new Color(140, 140, 140));
					txtFechaCaducidad_2.setText("AAAA-MM-DD");
				}
			}
		});
		txtFechaCaducidad_2.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		txtFechaCaducidad_2.setText("AAAA-MM-DD");
		txtFechaCaducidad_2.setForeground(new Color(0, 0, 0));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, txtFechaCaducidad_2, -2, SpringLayout.NORTH, lblFechaCaducidad_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.SOUTH, txtFechaCaducidad_2, 2, SpringLayout.SOUTH, lblFechaCaducidad_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, txtFechaCaducidad_2, 0, SpringLayout.WEST, txtCostoCompra_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.EAST, txtFechaCaducidad_2, 0, SpringLayout.EAST, txtCostoCompra_2);
		editaProductoPanel.add(txtFechaCaducidad_2);
		txtFechaCaducidad_2.setColumns(10);
		txtFechaCaducidad_2.setVisible(false);
		
		final JLabel lblAdvertencia_2 = new JLabel("");
		lblAdvertencia_2.setForeground(new Color(255, 127, 80));
		lblAdvertencia_2.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 16));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, lblAdvertencia_2, -65, SpringLayout.SOUTH, editaProductoPanel);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, lblAdvertencia_2, 0, SpringLayout.WEST, lblPropDatos_2);
		editaProductoPanel.add(lblAdvertencia_2);
		
		final JPanel editaPanel = new JPanel();
		editaPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				editaPanel.setBackground(new Color(204, 204, 204));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				editaPanel.setBackground(new Color(140, 140, 140));
			}
			@Override
			public void mouseClicked(MouseEvent e) { 
				if(flag.isEnabled())
				{
					Product p = producto;
					editProduct(p);
					card.show(menuProductosPanel, "2");
					DefaultTableModel model = (DefaultTableModel) tablaProductos.getModel();
					printWarehouse(model);
				}
			}
		});
		editaPanel.setBackground(new Color(140, 140, 140));
		sl_editaProductoPanel.putConstraint(SpringLayout.NORTH, editaPanel, -80, SpringLayout.SOUTH, editaProductoPanel);
		sl_editaProductoPanel.putConstraint(SpringLayout.SOUTH, editaPanel, -50, SpringLayout.SOUTH, editaProductoPanel);
		sl_editaProductoPanel.putConstraint(SpringLayout.WEST, editaPanel, 0, SpringLayout.WEST, txtPrecioVenta_2);
		sl_editaProductoPanel.putConstraint(SpringLayout.EAST, editaPanel, 0, SpringLayout.EAST, txtPrecioVenta_2);
		editaProductoPanel.add(editaPanel);
		SpringLayout sl_editaPanel = new SpringLayout();
		editaPanel.setLayout(sl_editaPanel);
		
		JLabel lblEdita = new JLabel("Editar producto");
		lblEdita.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 18));
		lblEdita.setForeground(new Color(255, 255, 255));
		sl_editaPanel.putConstraint(SpringLayout.SOUTH, lblEdita, -5, SpringLayout.SOUTH, editaPanel);
		sl_editaPanel.putConstraint(SpringLayout.NORTH, lblEdita, 5, SpringLayout.NORTH, editaPanel);
		sl_editaPanel.putConstraint(SpringLayout.EAST, lblEdita, -48, SpringLayout.EAST, editaPanel);
		sl_editaPanel.putConstraint(SpringLayout.WEST, lblEdita, 48, SpringLayout.WEST, editaPanel);
		editaPanel.add(lblEdita);
		
		//-----------------Componentes del panel de lista de productos----------------------------------
		final JLabel lblBack_1 = new JLabel("");
		lblBack_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblBack_1.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/back(w).png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblBack_1.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/back.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				lblBack_1.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/back.png")));
				//Removiendo todos los paneles
				/*menuProductosPanel.removeAll();
				menuProductosPanel.repaint();
				menuProductosPanel.revalidate();*/
				//Añadiendo panel de opciones
				/*menuProductosPanel.add(opcionesPanel);
				menuProductosPanel.repaint();
				menuProductosPanel.revalidate();*/
				card.show(menuProductosPanel, "1");
			}
		});
		lblBack_1.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/back.png")));
		sl_listaProductosPanel.putConstraint(SpringLayout.NORTH, lblBack_1, 0, SpringLayout.NORTH, listaProductosPanel);
		sl_listaProductosPanel.putConstraint(SpringLayout.WEST, lblBack_1, 0, SpringLayout.WEST, listaProductosPanel);
		sl_listaProductosPanel.putConstraint(SpringLayout.EAST, lblBack_1, 60, SpringLayout.WEST, listaProductosPanel);
		sl_listaProductosPanel.putConstraint(SpringLayout.SOUTH, lblBack_1, 40, SpringLayout.NORTH, listaProductosPanel);
		listaProductosPanel.add(lblBack_1);
		
		tablaProductos = new JTable();
		tablaProductos.setFont(new Font("SF Pro Display", Font.PLAIN, 14));
		tablaProductos.setBorder(null);
		tablaProductos.setRowHeight(20);
		sl_listaProductosPanel.putConstraint(SpringLayout.NORTH, tablaProductos, 100, SpringLayout.SOUTH, lblBack_1);
		sl_listaProductosPanel.putConstraint(SpringLayout.EAST, tablaProductos, -50, SpringLayout.EAST, listaProductosPanel);
		sl_listaProductosPanel.putConstraint(SpringLayout.WEST, tablaProductos, 50, SpringLayout.WEST, listaProductosPanel);
		sl_listaProductosPanel.putConstraint(SpringLayout.SOUTH, tablaProductos, -50, SpringLayout.SOUTH, listaProductosPanel);
		tablaProductos.setGridColor(new Color(255, 255, 255));
		tablaProductos.setSelectionBackground(new Color(240, 240, 240));
		tablaProductos.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Producto", "Marca", "Categoría", "Cantidad", "Unidad","CostoCompra","PrecioVenta", "FechaEntrada", "FechaCaducidad", "CodBar"
				}
				) {
					Class[] columnTypes = new Class[] {
						String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class
					};
					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
				});
		tablaProductos.setEnabled(false);
		listaProductosPanel.add(tablaProductos);
		
		JSeparator sep_header = new JSeparator();
		sep_header.setBackground(new Color(204, 204, 204));
		sep_header.setForeground(new Color(204, 204, 204));
		sl_listaProductosPanel.putConstraint(SpringLayout.SOUTH, sep_header, -5, SpringLayout.NORTH, tablaProductos);
		sl_listaProductosPanel.putConstraint(SpringLayout.EAST, sep_header, 0, SpringLayout.EAST, tablaProductos);
		sl_listaProductosPanel.putConstraint(SpringLayout.WEST, sep_header, 0, SpringLayout.WEST, tablaProductos);
		listaProductosPanel.add(sep_header);
		
		JSeparator sep_footer = new JSeparator();
		sep_header.setBackground(new Color(204, 204, 204));
		sep_header.setForeground(new Color(204, 204, 204));
		sl_listaProductosPanel.putConstraint(SpringLayout.NORTH, sep_footer, 5, SpringLayout.SOUTH, tablaProductos);
		sl_listaProductosPanel.putConstraint(SpringLayout.EAST, sep_footer, 0, SpringLayout.EAST, tablaProductos);
		sl_listaProductosPanel.putConstraint(SpringLayout.WEST, sep_footer, 0, SpringLayout.WEST, tablaProductos);
		listaProductosPanel.add(sep_footer);
		
		JPanel headerPanel_1 = new JPanel(new GridLayout(1,10));
		headerPanel_1.setBorder(null);
		headerPanel_1.setBackground(new Color(255, 255, 255));
		sl_listaProductosPanel.putConstraint(SpringLayout.NORTH, headerPanel_1, 36, SpringLayout.SOUTH, lblBack_1);
		sl_listaProductosPanel.putConstraint(SpringLayout.WEST, headerPanel_1, 0, SpringLayout.WEST, tablaProductos);
		sl_listaProductosPanel.putConstraint(SpringLayout.SOUTH, headerPanel_1, -10, SpringLayout.NORTH, tablaProductos);
		sl_listaProductosPanel.putConstraint(SpringLayout.EAST, headerPanel_1, 0, SpringLayout.EAST, tablaProductos);
		listaProductosPanel.add(headerPanel_1);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setForeground(new Color(102, 102, 102));
		lblNombre.setFont(new Font("SF Pro Display", Font.PLAIN, 12));
		headerPanel_1.add(lblNombre);
		
		JLabel lblMarca = new JLabel("Marca");
		lblMarca.setForeground(new Color(102, 102, 102));
		lblMarca.setFont(new Font("SF Pro Display", Font.PLAIN, 12));
		headerPanel_1.add(lblMarca);
		
		JLabel lblCategoria = new JLabel("Categor\u00EDa");
		lblCategoria.setForeground(new Color(102, 102, 102));
		lblCategoria.setFont(new Font("SF Pro Display", Font.PLAIN, 12));
		headerPanel_1.add(lblCategoria);
		
		JLabel lblCantidad = new JLabel("Cantidad");
		lblCantidad.setForeground(new Color(102, 102, 102));
		lblCantidad.setFont(new Font("SF Pro Display", Font.PLAIN, 12));
		headerPanel_1.add(lblCantidad);
		
		JLabel lblUnidad = new JLabel("Unidad");
		lblUnidad.setForeground(new Color(102, 102, 102));
		lblUnidad.setFont(new Font("SF Pro Display", Font.PLAIN, 12));
		headerPanel_1.add(lblUnidad);
		
		JLabel lblCostoCompra = new JLabel("COSTO (compra)");
		lblCostoCompra.setFont(new Font("SF Pro Display", Font.PLAIN, 12));
		lblCostoCompra.setForeground(new Color(102, 102, 102));
		headerPanel_1.add(lblCostoCompra);
		
		JLabel lblPrecioVenta = new JLabel("PRECIO (venta)");
		lblPrecioVenta.setForeground(new Color(102, 102, 102));
		lblPrecioVenta.setFont(new Font("SF Pro Display", Font.PLAIN, 12));
		headerPanel_1.add(lblPrecioVenta);
		
		JLabel lblFechaEntrada = new JLabel("Fecha entrada");
		lblFechaEntrada.setForeground(new Color(102, 102, 102));
		lblFechaEntrada.setFont(new Font("SF Pro Display", Font.PLAIN, 12));
		headerPanel_1.add(lblFechaEntrada);
		
		JLabel lblFechaCaducidad = new JLabel("Fecha caducidad");
		lblFechaCaducidad.setForeground(new Color(102, 102, 102));
		lblFechaCaducidad.setFont(new Font("SF Pro Display", Font.PLAIN, 12));
		headerPanel_1.add(lblFechaCaducidad);
		
		JLabel lblCodBar = new JLabel("Cod. de barras");
		lblCodBar.setForeground(new Color(102, 102, 102));
		lblCodBar.setFont(new Font("SF Pro Display", Font.PLAIN, 14));
		headerPanel_1.add(lblCodBar);
		
		final JPanel agregarPanel = new JPanel();
		agregarPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				agregarPanel.setBackground(new Color(204, 204, 204));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				agregarPanel.setBackground(new Color(140, 140, 140));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				agregarPanel.setBackground(new Color(140, 140, 140));
				card.show(menuProductosPanel, "5");
				TimeService ts = new TimeService();
				txtFechaEntrada_1.setText(ts.getDate());
			}
		});
		agregarPanel.setBackground(new Color(140, 140, 140));
		sl_listaProductosPanel.putConstraint(SpringLayout.WEST, agregarPanel, -200, SpringLayout.EAST, tablaProductos);
		sl_listaProductosPanel.putConstraint(SpringLayout.NORTH, agregarPanel, 0, SpringLayout.SOUTH, lblBack_1);
		sl_listaProductosPanel.putConstraint(SpringLayout.SOUTH, agregarPanel, 30, SpringLayout.SOUTH, lblBack_1);
		sl_listaProductosPanel.putConstraint(SpringLayout.EAST, agregarPanel, 0, SpringLayout.EAST, tablaProductos);
		listaProductosPanel.add(agregarPanel);
		SpringLayout sl_agregarPanel = new SpringLayout();
		agregarPanel.setLayout(sl_agregarPanel);
		
		JLabel lblAgregarProducto = new JLabel("Agregar producto");
		sl_agregarPanel.putConstraint(SpringLayout.WEST, lblAgregarProducto, 23, SpringLayout.WEST, agregarPanel);
		sl_agregarPanel.putConstraint(SpringLayout.SOUTH, lblAgregarProducto, -5, SpringLayout.SOUTH, agregarPanel);
		sl_agregarPanel.putConstraint(SpringLayout.NORTH, lblAgregarProducto, 5, SpringLayout.NORTH, agregarPanel);
		lblAgregarProducto.setForeground(new Color(255, 255, 255));
		lblAgregarProducto.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 18));
		agregarPanel.add(lblAgregarProducto);
		
		final JPanel editarPanel = new JPanel();
		sl_listaProductosPanel.putConstraint(SpringLayout.NORTH, editarPanel, 0, SpringLayout.NORTH, agregarPanel);
		sl_listaProductosPanel.putConstraint(SpringLayout.WEST, editarPanel, -220, SpringLayout.WEST, agregarPanel);
		sl_listaProductosPanel.putConstraint(SpringLayout.SOUTH, editarPanel, 0, SpringLayout.SOUTH, agregarPanel);
		sl_listaProductosPanel.putConstraint(SpringLayout.EAST, editarPanel, -20, SpringLayout.WEST, agregarPanel);
		editarPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				editarPanel.setBackground(new Color(204, 204, 204));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				editarPanel.setBackground(new Color(140, 140, 140));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				editarPanel.setBackground(new Color(140, 140, 140));
				String usrnm = JOptionPane.showInputDialog(null, "Para continuar, ingresa un \nusuariode administrador\n\n", "Ejecutar como administrador", JOptionPane.QUESTION_MESSAGE);
				String pswd = JOptionPane.showInputDialog(null, "Proporciona la \ncontraseña\n\n", "Ejecutar como administrador", JOptionPane.QUESTION_MESSAGE);
				try { 
					User user = new User(usrnm, Toolkit.strToMD5(pswd));
					AuthenticationService as = new AuthenticationService();
					user = as.searchUser(user);
					if(user != null)
					{
						if(user.getUserGroup().getGroup().equals("admin"))
							card.show(menuProductosPanel, "6");
						else
							JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
					}
					else
						JOptionPane.showMessageDialog(null, "Usuario no encontrado", "Error de autenticación", JOptionPane.ERROR_MESSAGE);
				} catch(ToolkitException e1) {
					e1.printStackTrace();
				}
			}
		});
		editarPanel.setBackground(new Color(140, 140, 140));
		listaProductosPanel.add(editarPanel);
		SpringLayout sl_editarPanel = new SpringLayout();
		editarPanel.setLayout(sl_editarPanel);
		
		JLabel lblEditarProducto = new JLabel("Editar producto");
		lblEditarProducto.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 18));
		lblEditarProducto.setForeground(new Color(255, 255, 255));
		sl_editarPanel.putConstraint(SpringLayout.WEST, lblEditarProducto, 35, SpringLayout.WEST, editarPanel);
		sl_editarPanel.putConstraint(SpringLayout.SOUTH, lblEditarProducto, -5, SpringLayout.SOUTH, editarPanel);
		editarPanel.add(lblEditarProducto);
		
		final JPanel mostrarPanel = new JPanel();
		mostrarPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mostrarPanel.setBackground(new Color(204, 204, 204));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mostrarPanel.setBackground(new Color(140, 140, 140));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel) tablaProductos.getModel();
				printWarehouse(model);
			}
		});
		mostrarPanel.setBackground(new Color(140, 140, 140));
		sl_listaProductosPanel.putConstraint(SpringLayout.NORTH, mostrarPanel, 0, SpringLayout.NORTH, agregarPanel);
		sl_listaProductosPanel.putConstraint(SpringLayout.SOUTH, mostrarPanel, 0, SpringLayout.SOUTH, agregarPanel);
		sl_listaProductosPanel.putConstraint(SpringLayout.EAST, mostrarPanel, -20, SpringLayout.WEST, editarPanel);
		sl_listaProductosPanel.putConstraint(SpringLayout.WEST, mostrarPanel, -220, SpringLayout.WEST, editarPanel);
		listaProductosPanel.add(mostrarPanel);
		SpringLayout sl_mostrarPanel = new SpringLayout();
		mostrarPanel.setLayout(sl_mostrarPanel);
		
		JLabel lblNewLabel_1 = new JLabel("Mostrar inventario");
		lblNewLabel_1.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 18));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		sl_mostrarPanel.putConstraint(SpringLayout.WEST, lblNewLabel_1, 25, SpringLayout.WEST, mostrarPanel);
		sl_mostrarPanel.putConstraint(SpringLayout.SOUTH, lblNewLabel_1, -5, SpringLayout.SOUTH, mostrarPanel);
		mostrarPanel.add(lblNewLabel_1);
		
		txtBuscarProducto = new JTextField();
		txtBuscarProducto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		txtBuscarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!txtBuscarProducto.getText().equals(""))
				{
					Product p = searchProduct(txtBuscarProducto.getText());
					if(p != null)
					{
						DefaultTableModel model = (DefaultTableModel) tablaProductos.getModel();
						cleanTable(model);
						addProductRow(model, p);
					}
					else
						JOptionPane.showMessageDialog(null, "No se encontraron coincidencias", "Producto no encontrado", JOptionPane.ERROR_MESSAGE);
					txtBuscarProducto.setText("");
				}
			}
		});
		txtBuscarProducto.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(txtBuscarProducto.getText().equals("  Buscar en el inventario"))
				{
					txtBuscarProducto.setText("");
					txtBuscarProducto.setForeground(new Color(100, 100, 100));
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtBuscarProducto.getText().equals(""))
				{
					txtBuscarProducto.setText("  Buscar en el inventario");
					txtBuscarProducto.setForeground(new Color(140, 140, 140));
				}
			}
		});
		txtBuscarProducto.setForeground(new Color(140, 140, 140));
		txtBuscarProducto.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		txtBuscarProducto.setText("  Buscar en el inventario");
		txtBuscarProducto.setBackground(new Color(220, 220, 220));
		txtBuscarProducto.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		sl_listaProductosPanel.putConstraint(SpringLayout.NORTH, txtBuscarProducto, 0, SpringLayout.NORTH, mostrarPanel);
		sl_listaProductosPanel.putConstraint(SpringLayout.SOUTH, txtBuscarProducto, 0, SpringLayout.SOUTH, mostrarPanel);
		sl_listaProductosPanel.putConstraint(SpringLayout.WEST, txtBuscarProducto, 20, SpringLayout.EAST, lblBack_1);
		sl_listaProductosPanel.putConstraint(SpringLayout.EAST, txtBuscarProducto, -100, SpringLayout.WEST, mostrarPanel);
		listaProductosPanel.add(txtBuscarProducto);
		txtBuscarProducto.setColumns(10);
		
		final JLabel lblBuscarProductoIcon = new JLabel("");
		lblBuscarProductoIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblBuscarProductoIcon.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/search(w).png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblBuscarProductoIcon.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/search.png")));
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(!txtBuscarProducto.getText().equals("") && !txtBuscarProducto.getText().equals("  Buscar en el inventario") )
				{
					Product p = searchProduct(txtBuscarProducto.getText());
					if(p != null)
					{
						DefaultTableModel model = (DefaultTableModel) tablaProductos.getModel();
						cleanTable(model);
						addProductRow(model, p);
					}
					else
						JOptionPane.showMessageDialog(null, "No se encontraron coincidencias", "Producto no encontrado", JOptionPane.ERROR_MESSAGE);
					txtBuscarProducto.setForeground(new Color(153, 153, 153));
					txtBuscarProducto.setText("");
				}
			}
		});
		lblBuscarProductoIcon.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/search.png")));
		sl_listaProductosPanel.putConstraint(SpringLayout.NORTH, lblBuscarProductoIcon, 5, SpringLayout.NORTH, agregarPanel);
		sl_listaProductosPanel.putConstraint(SpringLayout.WEST, lblBuscarProductoIcon, 10, SpringLayout.EAST, txtBuscarProducto);
		listaProductosPanel.add(lblBuscarProductoIcon);
		
		//-----------ELEMENTOS DEL PANEL DE LISTA DE CATEGORÍAS----------- (ESTÁTICOS)
		final JLabel lblBack_2 = new JLabel("");
		lblBack_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblBack_2.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/back(w).png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblBack_2.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/back.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				lblBack_2.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/back.png")));
				//Removiendo todos los paneles
				/*menuProductosPanel.removeAll();
				menuProductosPanel.repaint();
				menuProductosPanel.revalidate();*/
				//Añadiendo panel de opciones
				/*menuProductosPanel.add(opcionesPanel);
				menuProductosPanel.repaint();
				menuProductosPanel.revalidate();*/
				card.show(menuProductosPanel, "1");
			}
		});
		lblBack_2.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/back.png")));
		sl_listaCategoriasPanel.putConstraint(SpringLayout.NORTH, lblBack_2, 0, SpringLayout.NORTH, listaCategoriasPanel);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.WEST, lblBack_2, 0, SpringLayout.WEST, listaCategoriasPanel);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.SOUTH, lblBack_2, 40, SpringLayout.NORTH, listaCategoriasPanel);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.EAST, lblBack_2, 60, SpringLayout.WEST, listaCategoriasPanel);
		listaCategoriasPanel.add(lblBack_2);
		
		JLabel lblNewCategory = new JLabel("Nueva categor\u00EDa");
		lblNewCategory.setForeground(new Color(102, 102, 102));
		lblNewCategory.setFont(new Font("SF Pro Display", Font.PLAIN, 26));
		sl_listaCategoriasPanel.putConstraint(SpringLayout.NORTH, lblNewCategory, 10, SpringLayout.SOUTH, lblBack_2);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.WEST, lblNewCategory, 50, SpringLayout.WEST, listaCategoriasPanel);
		listaCategoriasPanel.add(lblNewCategory);
		
		final JLabel lblPropCategory = new JLabel("Proporciona el nombre de la nueva categor\u00EDa.");
		sl_listaCategoriasPanel.putConstraint(SpringLayout.NORTH, lblPropCategory, 20, SpringLayout.SOUTH, lblNewCategory);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.WEST, lblPropCategory, 0, SpringLayout.WEST, lblNewCategory);
		lblPropCategory.setForeground(new Color(102, 102, 102));
		lblPropCategory.setFont(new Font("SF Pro Display", Font.PLAIN, 18));
		listaCategoriasPanel.add(lblPropCategory);
		
		JLabel lblCategory = new JLabel("Categor\u00EDa");
		sl_listaCategoriasPanel.putConstraint(SpringLayout.NORTH, lblCategory, 10, SpringLayout.SOUTH, lblPropCategory);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.WEST, lblCategory, 50, SpringLayout.WEST, listaCategoriasPanel);
		lblCategory.setForeground(new Color(102, 102, 102));
		lblCategory.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		listaCategoriasPanel.add(lblCategory);
 
		txtCategory = new JTextField();
		txtCategory.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtCategory.setBackground(new Color(225, 225, 225));
		txtCategory.setForeground(new Color(0, 0, 0));
		txtCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { //Método que crea una nueva categoría 
				if(!txtCategory.getText().equals(""))
				{
					String result = insertProductType();
					if(result.equals(""))
					{
						DefaultTableModel model = (DefaultTableModel) tablaCategorias.getModel();		//Obtenemos el modelo de la tabla de categorías
						printCategories(model);
					}
					else
					{
						JOptionPane.showMessageDialog(null, result, "Error al añadir categoría", JOptionPane.ERROR_MESSAGE);
						DefaultTableModel model = (DefaultTableModel) tablaCategorias.getModel();		//Obtenemos el modelo de la tabla de categorías
						printCategories(model);
					}
					txtCategory.setText("");
				}
			}
		});
		txtCategory.setFont(new Font("Futura Lt BT", Font.PLAIN, 18));
		sl_listaCategoriasPanel.putConstraint(SpringLayout.NORTH, txtCategory, -2, SpringLayout.NORTH, lblCategory);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.SOUTH, txtCategory, 2, SpringLayout.SOUTH, lblCategory);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.WEST, txtCategory, 10, SpringLayout.EAST, lblCategory);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.EAST, txtCategory, 210, SpringLayout.EAST, lblCategory);
		listaCategoriasPanel.add(txtCategory);
		txtCategory.setColumns(10);
		
		final JPanel newCategoryPanel = new JPanel();
		newCategoryPanel.setBorder(null);
		newCategoryPanel.setBackground(new Color(255, 255, 255));
		sl_listaCategoriasPanel.putConstraint(SpringLayout.WEST, newCategoryPanel, 25, SpringLayout.EAST, txtCategory);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.EAST, newCategoryPanel, 120, SpringLayout.EAST, txtCategory);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.SOUTH, newCategoryPanel, 2, SpringLayout.SOUTH, lblCategory);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.NORTH, newCategoryPanel, -2, SpringLayout.NORTH, lblCategory);
		listaCategoriasPanel.add(newCategoryPanel);
		SpringLayout sl_newCategoryPanel = new SpringLayout();
		newCategoryPanel.setLayout(sl_newCategoryPanel);
		
		final JLabel lblNewCategory_1 = new JLabel("A\u00F1adir");
		lblNewCategory_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewCategory_1.setForeground(new Color(51, 153, 255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblNewCategory_1.setForeground(new Color(51, 102, 204));
			}
			@Override
			public void mouseClicked(MouseEvent e) { //Realiza la misma acción que el textField txtCategory
				if(!txtCategory.getText().equals(""))
				{
					String result = insertProductType();
					if(result.equals(""))
					{
						DefaultTableModel model = (DefaultTableModel) tablaCategorias.getModel();		//Obtenemos el modelo de la tabla de categorías
						printCategories(model);
					}
					else
					{
						JOptionPane.showMessageDialog(null, result, "Error al añadir categoría", JOptionPane.ERROR_MESSAGE);
						DefaultTableModel model = (DefaultTableModel) tablaCategorias.getModel();		//Obtenemos el modelo de la tabla de categorías
						printCategories(model);
					}
					txtCategory.setText("");
				}
			}
		});
		lblNewCategory_1.setForeground(new Color(51, 102, 204));
		lblNewCategory_1.setFont(new Font("SF Pro Display", Font.PLAIN, 18));
		sl_newCategoryPanel.putConstraint(SpringLayout.NORTH, lblNewCategory_1, 4, SpringLayout.NORTH, newCategoryPanel);
		sl_newCategoryPanel.putConstraint(SpringLayout.SOUTH, lblNewCategory_1, -4, SpringLayout.SOUTH, newCategoryPanel);
		sl_newCategoryPanel.putConstraint(SpringLayout.WEST, lblNewCategory_1, 0, SpringLayout.WEST, newCategoryPanel);
		newCategoryPanel.add(lblNewCategory_1);
		
		JLabel lblEditCategory = new JLabel("Editar categor\u00EDa");
		lblEditCategory.setFont(new Font("SF Pro Display", Font.PLAIN, 26));
		lblEditCategory.setForeground(new Color(102, 102, 102));
		sl_listaCategoriasPanel.putConstraint(SpringLayout.NORTH, lblEditCategory, 50, SpringLayout.SOUTH, lblCategory);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.WEST, lblEditCategory, 0, SpringLayout.WEST, lblNewCategory);
		listaCategoriasPanel.add(lblEditCategory);
		
		JLabel lblPropCategory_1 = new JLabel("Proporciona el nombre de la categor\u00EDa a editar.");
		lblPropCategory_1.setForeground(new Color(102, 102, 102));
		lblPropCategory_1.setFont(new Font("SF Pro Display", Font.PLAIN, 18));
		sl_listaCategoriasPanel.putConstraint(SpringLayout.NORTH, lblPropCategory_1, 20, SpringLayout.SOUTH, lblEditCategory);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.WEST, lblPropCategory_1, 0, SpringLayout.WEST, lblEditCategory);
		listaCategoriasPanel.add(lblPropCategory_1);
		
		JLabel lblCategory_1 = new JLabel("Categor\u00EDa");
		lblCategory_1.setForeground(new Color(102, 102, 102));
		lblCategory_1.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_listaCategoriasPanel.putConstraint(SpringLayout.WEST, lblCategory_1, 0, SpringLayout.WEST, lblEditCategory);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.NORTH, lblCategory_1, 10, SpringLayout.SOUTH, lblPropCategory_1);
		listaCategoriasPanel.add(lblCategory_1);
		
		txtCategory_1 = new JTextField();
		txtCategory_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!txtCategory_1.getText().equals(""))
				{
					ProductType productType = searchProductType(txtCategory_1.getText());
					String result = editProductType(productType);
					System.out.println(result);
					if(result.equals(""))
					{
						DefaultTableModel model = (DefaultTableModel) tablaCategorias.getModel();		//Obtenemos el modelo de la tabla de categorías
						printCategories(model);
					}
					else
					{
						JOptionPane.showMessageDialog(null, result, "Error al editar categoría", JOptionPane.ERROR_MESSAGE);
						DefaultTableModel model = (DefaultTableModel) tablaCategorias.getModel();		//Obtenemos el modelo de la tabla de categorías
						printCategories(model);
					}
					txtCategory_1.setText("");
				}
			}
		});
		txtCategory_1.setBackground(new Color(225, 225, 225));
		txtCategory_1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtCategory_1.setFont(new Font("Futura Lt BT", Font.PLAIN, 18));
		sl_listaCategoriasPanel.putConstraint(SpringLayout.WEST, txtCategory_1, 10, SpringLayout.EAST, lblCategory_1);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.EAST, txtCategory_1, 210, SpringLayout.EAST, lblCategory_1);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.NORTH, txtCategory_1, -2, SpringLayout.NORTH, lblCategory_1);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.SOUTH, txtCategory_1, 2, SpringLayout.SOUTH, lblCategory_1);
		listaCategoriasPanel.add(txtCategory_1);
		txtCategory_1.setColumns(10);
		
		final JPanel editCategoryPanel = new JPanel();
		sl_listaCategoriasPanel.putConstraint(SpringLayout.SOUTH, editCategoryPanel, 2, SpringLayout.SOUTH, lblCategory_1);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.NORTH, editCategoryPanel, -2, SpringLayout.NORTH, lblCategory_1);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.WEST, editCategoryPanel, 25, SpringLayout.EAST, txtCategory_1);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.EAST, editCategoryPanel, 0, SpringLayout.EAST, newCategoryPanel);
		editCategoryPanel.setBackground(new Color(255, 255, 255));
		editCategoryPanel.setForeground(new Color(0, 0, 0));
		listaCategoriasPanel.add(editCategoryPanel);
		SpringLayout sl_editCategoryPanel = new SpringLayout();
		editCategoryPanel.setLayout(sl_editCategoryPanel);
		
		final JLabel lblEditCategory_1 = new JLabel("Editar");
		lblEditCategory_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblEditCategory_1.setForeground(new Color(51, 153, 255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblEditCategory_1.setForeground(new Color(51, 102, 204));
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(!txtCategory_1.getText().equals(""))
				{
					//Buscamos la categoría
					/*com.mexicacode.puntoventa.pojos.Type t = new com.mexicacode.puntoventa.pojos.Type(txtCategory_1.getText().toLowerCase());
					TypeService ts = new TypeService();
					t = ts.searchType(t);
					if(t == null) 	//Si no se encuentra, lanza error
					{
						JOptionPane.showMessageDialog(null, "No se encontró la categoría buscada", "Categoría no encontrada", JOptionPane.ERROR_MESSAGE);
						txtCategory_1.setText("");
					}
					else			//Si sí, actualiza el registro e imprime la tabla de categorías
					{
						String cad = JOptionPane.showInputDialog(null, "Proporcione el nuevo \nNombre de la categoría", "Editar categoría", JOptionPane.PLAIN_MESSAGE);
						t.setType(cad);
						ts.updateType(t);																//Actualizamos la categoría
						List<com.mexicacode.puntoventa.pojos.Type> data = ts.showTypes();				//Retornamos la lista de todas las categorías existentes
						DefaultTableModel model = (DefaultTableModel) tablaCategorias.getModel();		//Obtenemos el modelo de la tabla
						while(model.getRowCount() > 0)													//Lo limpiamos
						{
						    model.removeRow(0);
						}
						for(int i=0;i<data.size();i++)  												//Añadimos los registros encontrados a la tabla
						{
							model.addRow(new String[] {data.get(i).getType()});
						}										
						txtCategory_1.setText("");
					}
					*/
				}
			}
		});
		lblEditCategory_1.setFont(new Font("SF Pro Display", Font.PLAIN, 18));
		lblEditCategory_1.setForeground(new Color(51, 102, 204));
		sl_editCategoryPanel.putConstraint(SpringLayout.NORTH, lblEditCategory_1, 4, SpringLayout.NORTH, editCategoryPanel);
		sl_editCategoryPanel.putConstraint(SpringLayout.SOUTH, lblEditCategory_1, -4, SpringLayout.SOUTH, editCategoryPanel);
		sl_editCategoryPanel.putConstraint(SpringLayout.WEST, lblEditCategory_1, 0, SpringLayout.WEST, editCategoryPanel);
		editCategoryPanel.add(lblEditCategory_1);
		
		final JLabel lblShowCategories = new JLabel("Mostrar categor\u00EDas");
		lblShowCategories.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblShowCategories.setForeground(new Color(51, 153, 255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblShowCategories.setForeground(new Color(51, 102, 204));
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {//Método que retorna la lista de categorías existentes en la base de datos y las imprime en una tabla
				DefaultTableModel model = (DefaultTableModel) tablaCategorias.getModel();		//Obtenemos el modelo de la tabla de categorías
				printCategories(model);
			}
		});
		lblShowCategories.setForeground(new Color(51, 102, 204));
		lblShowCategories.setFont(new Font("SF Pro Display", Font.PLAIN, 26));
		sl_listaCategoriasPanel.putConstraint(SpringLayout.NORTH, lblShowCategories, 50, SpringLayout.SOUTH, lblCategory_1);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.WEST, lblShowCategories, 0, SpringLayout.WEST, lblNewCategory);
		listaCategoriasPanel.add(lblShowCategories);
		
		//Creamos una tabla estática contenedora de las categorías
		tablaCategorias = new JTable();
		tablaCategorias.setEnabled(false);
		tablaCategorias.setFont(new Font("SF Pro Display", Font.PLAIN, 14));
		tablaCategorias.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Categorias"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tablaCategorias.setBorder(null);
		tablaCategorias.setGridColor(new Color(255, 255, 255));
		tablaCategorias.setRowHeight(20);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.NORTH, tablaCategorias, 0, SpringLayout.NORTH, lblPropCategory);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.SOUTH, tablaCategorias, -50, SpringLayout.SOUTH, listaCategoriasPanel);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.WEST, tablaCategorias, 150, SpringLayout.EAST, lblPropCategory);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.EAST, tablaCategorias, -50, SpringLayout.EAST, listaCategoriasPanel);
		listaCategoriasPanel.add(tablaCategorias);
		
		txtBuscarCategoria = new JTextField();
		txtBuscarCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { //Método que busca una categoría en la base de datos y la imprime en la tabla de categorías
				if(!txtBuscarCategoria.getText().equals(""))
				{
					ProductType pt = searchProductType(txtBuscarCategoria.getText());
					if(pt != null)
					{
						DefaultTableModel model = (DefaultTableModel) tablaCategorias.getModel();
						cleanTable(model);
						addCategoryRow(model, pt);
					}
					else
						JOptionPane.showMessageDialog(null, "No se encontraron coincidencias", "Categoría no encontrada", JOptionPane.ERROR_MESSAGE);
					txtBuscarCategoria.setText("");
				}
			}
		});
		txtBuscarCategoria.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(txtBuscarCategoria.getText().equals("  Buscar categoría"))
				{
					txtBuscarCategoria.setForeground(new Color(100, 100, 100));
					txtBuscarCategoria.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				if(txtBuscarCategoria.getText().equals(""))
				{
					txtBuscarCategoria.setForeground(new Color(140, 140, 140));
					txtBuscarCategoria.setText("  Buscar categoría");
				}
			}
		});
		txtBuscarCategoria.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtBuscarCategoria.setBackground(new Color(225, 225, 225));
		txtBuscarCategoria.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		txtBuscarCategoria.setText("  Buscar categoría");
		txtBuscarCategoria.setForeground(new Color(140, 140, 140));
		sl_listaCategoriasPanel.putConstraint(SpringLayout.WEST, txtBuscarCategoria, 0, SpringLayout.WEST, tablaCategorias);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.EAST, txtBuscarCategoria, -30, SpringLayout.EAST, tablaCategorias);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.SOUTH, txtBuscarCategoria, -2, SpringLayout.SOUTH, lblNewCategory);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.NORTH, txtBuscarCategoria, 2, SpringLayout.NORTH, lblNewCategory);
		listaCategoriasPanel.add(txtBuscarCategoria);
		txtBuscarCategoria.setColumns(10);
		
		final JLabel lblSearch = new JLabel("");
		lblSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblSearch.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/search(w).png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblSearch.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/search.png")));
			}
			@Override
			public void mouseClicked(MouseEvent arg0) { //Método que realiza la misma acción que el textField txtSearch
				ProductType pt = searchProductType(txtBuscarCategoria.getText());
				if(pt != null)
				{
					DefaultTableModel model = (DefaultTableModel) tablaCategorias.getModel();
					cleanTable(model);
					addCategoryRow(model, pt);
				}
				else
					JOptionPane.showMessageDialog(null, "No se encontraron coincidencias", "Categoría no encontrada", JOptionPane.ERROR_MESSAGE);
				txtBuscarCategoria.setText("");
			}
		});
		lblSearch.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/search.png")));
		sl_listaCategoriasPanel.putConstraint(SpringLayout.NORTH, lblSearch, 5, SpringLayout.NORTH, txtBuscarCategoria);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.EAST, lblSearch, 0, SpringLayout.EAST, tablaCategorias);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.WEST, lblSearch, -20, SpringLayout.EAST, tablaCategorias);
		listaCategoriasPanel.add(lblSearch);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(204, 204, 204));
		separator.setOrientation(SwingConstants.VERTICAL);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.NORTH, separator, 0, SpringLayout.NORTH, txtBuscarCategoria);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.SOUTH, separator, 0, SpringLayout.SOUTH, lblShowCategories);
		sl_listaCategoriasPanel.putConstraint(SpringLayout.EAST, separator, -70, SpringLayout.WEST, txtBuscarCategoria);
		listaCategoriasPanel.add(separator);
		
		//----------------Componentes del panel de próximas compras------------------
		final JLabel lblBack_3 = new JLabel("");
		lblBack_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblBack_3.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/back(w).png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblBack_3.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/back.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				lblBack_3.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/back.png")));
				//Removiendo todos los paneles
				/*menuProductosPanel.removeAll();
				menuProductosPanel.repaint();
				menuProductosPanel.revalidate();*/
				//Añadiendo panel de opciones
				/*menuProductosPanel.add(opcionesPanel);
				menuProductosPanel.repaint();
				menuProductosPanel.revalidate();*/
				card.show(menuProductosPanel, "1");
			}
		});
		lblBack_3.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/back.png")));
		sl_proxComprasPanel.putConstraint(SpringLayout.NORTH, lblBack_3, 0, SpringLayout.NORTH, proxComprasPanel);
		sl_proxComprasPanel.putConstraint(SpringLayout.WEST, lblBack_3, 0, SpringLayout.WEST, proxComprasPanel);
		sl_proxComprasPanel.putConstraint(SpringLayout.SOUTH, lblBack_3, 40, SpringLayout.NORTH, proxComprasPanel);
		sl_proxComprasPanel.putConstraint(SpringLayout.EAST, lblBack_3, 60, SpringLayout.WEST, proxComprasPanel);
		proxComprasPanel.add(lblBack_3);
		
		//-------------Elementos del panel de agregar productos----------------
		final JLabel lblBack_4 = new JLabel("");
		lblBack_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblBack_4.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/back(w).png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblBack_4.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/back.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				lblBack_4.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/back.png")));
				card.show(menuProductosPanel, "2");
			}
		});
		lblBack_4.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/back.png")));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, lblBack_4, 0, SpringLayout.NORTH, agregaProductoPanel);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, lblBack_4, 0, SpringLayout.WEST, agregaProductoPanel);
		sl_agregaProductoPanel.putConstraint(SpringLayout.SOUTH, lblBack_4, 40, SpringLayout.NORTH, agregaProductoPanel);
		sl_agregaProductoPanel.putConstraint(SpringLayout.EAST, lblBack_4, 60, SpringLayout.WEST, agregaProductoPanel);
		agregaProductoPanel.add(lblBack_4);
		
		JLabel lblPropDatos_1 = new JLabel("Proporciona los datos del producto a agregar.");
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, lblPropDatos_1, 6, SpringLayout.SOUTH, lblBack_4);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, lblPropDatos_1, 50, SpringLayout.WEST, agregaProductoPanel);
		lblPropDatos_1.setForeground(new Color(102, 102, 102));
		lblPropDatos_1.setFont(new Font("SF Pro Display", Font.PLAIN, 20));
		agregaProductoPanel.add(lblPropDatos_1);
		
		final JLabel lblNomProduct_1 = new JLabel("Nombre del producto");
		lblNomProduct_1.setForeground(new Color(102, 102, 102));
		lblNomProduct_1.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, lblNomProduct_1, 60, SpringLayout.SOUTH, lblBack_4);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, lblNomProduct_1, 50, SpringLayout.WEST, agregaProductoPanel);
		agregaProductoPanel.add(lblNomProduct_1);
		
		txtNomProduct_1 = new JTextField();
		txtNomProduct_1.setBackground(new Color(225, 225, 225));
		txtNomProduct_1.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		txtNomProduct_1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, txtNomProduct_1, -2, SpringLayout.NORTH, lblNomProduct_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, txtNomProduct_1, 15, SpringLayout.EAST, lblNomProduct_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.SOUTH, txtNomProduct_1, 2, SpringLayout.SOUTH, lblNomProduct_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.EAST, txtNomProduct_1, 265, SpringLayout.EAST, lblNomProduct_1);
		agregaProductoPanel.add(txtNomProduct_1);
		txtNomProduct_1.setColumns(10);
		
		JLabel lblCodBar_1 = new JLabel("C\u00F3digo de barras");
		lblCodBar_1.setForeground(new Color(100, 100, 100));
		lblCodBar_1.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, lblCodBar_1, 25, SpringLayout.SOUTH, lblNomProduct_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, lblCodBar_1, 0, SpringLayout.WEST, lblNomProduct_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.EAST, lblCodBar_1, 0, SpringLayout.EAST, lblNomProduct_1);
		agregaProductoPanel.add(lblCodBar_1);
		
		txtCodBar_1 = new JTextField();
		txtCodBar_1.setBackground(new Color(225, 225, 225));
		txtCodBar_1.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		txtCodBar_1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, txtCodBar_1, 0, SpringLayout.WEST, txtNomProduct_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.EAST, txtCodBar_1, 0, SpringLayout.EAST, txtNomProduct_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.SOUTH, txtCodBar_1, 2, SpringLayout.SOUTH, lblCodBar_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, txtCodBar_1, -2, SpringLayout.NORTH, lblCodBar_1);
		agregaProductoPanel.add(txtCodBar_1);
		txtCodBar_1.setColumns(10);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, txtCodBar_1, 0, SpringLayout.WEST, txtNomProduct_1);
		
		JLabel lblMarca_1 = new JLabel("Marca");
		lblMarca_1.setForeground(new Color(100, 100, 100));
		lblMarca_1.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, lblMarca_1, 25, SpringLayout.SOUTH, lblCodBar_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, lblMarca_1, 0, SpringLayout.WEST, lblNomProduct_1);
		agregaProductoPanel.add(lblMarca_1);
		
		txtMarca_1 = new JTextField();
		txtMarca_1.setBackground(new Color(225, 225, 225));
		txtMarca_1.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		txtMarca_1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, txtMarca_1, 0, SpringLayout.WEST, txtNomProduct_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.EAST, txtMarca_1, 0, SpringLayout.EAST, txtNomProduct_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.SOUTH, txtMarca_1, 2, SpringLayout.SOUTH, lblMarca_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, txtMarca_1, -2, SpringLayout.NORTH, lblMarca_1);
		agregaProductoPanel.add(txtMarca_1);
		txtMarca_1.setColumns(10);
		
		JLabel lblCategoria_1 = new JLabel("Categor\u00EDa");
		lblCategoria_1.setForeground(new Color(100, 100, 100));
		lblCategoria_1.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, lblCategoria_1, 25, SpringLayout.SOUTH, lblMarca_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, lblCategoria_1, 0, SpringLayout.WEST, lblPropDatos_1);
		agregaProductoPanel.add(lblCategoria_1);
		
		txtCategoria_1 = new JTextField();
		txtCategoria_1.setBackground(new Color(225, 225, 225));
		txtCategoria_1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtCategoria_1.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, txtCategoria_1, 0, SpringLayout.WEST, txtNomProduct_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.SOUTH, txtCategoria_1, 2, SpringLayout.SOUTH, lblCategoria_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, txtCategoria_1, -2, SpringLayout.NORTH, lblCategoria_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.EAST, txtCategoria_1, 0, SpringLayout.EAST, txtNomProduct_1);
		agregaProductoPanel.add(txtCategoria_1);
		txtCategoria_1.setColumns(10);
		
		JLabel lblCantidad_1 = new JLabel("Cantidad");
		lblCantidad_1.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		lblCantidad_1.setForeground(new Color(100, 100, 100));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, lblCantidad_1, 25, SpringLayout.SOUTH, lblCategoria_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, lblCantidad_1, 0, SpringLayout.WEST, lblPropDatos_1);
		agregaProductoPanel.add(lblCantidad_1);
		
		txtCantidad_1 = new JTextField();
		txtCantidad_1.setBackground(new Color(225, 225, 225));
		txtCantidad_1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtCantidad_1.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, txtCantidad_1, -2, SpringLayout.NORTH, lblCantidad_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.SOUTH, txtCantidad_1, 2, SpringLayout.SOUTH, lblCantidad_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, txtCantidad_1, 0, SpringLayout.WEST, txtNomProduct_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.EAST, txtCantidad_1, 0, SpringLayout.EAST, txtNomProduct_1);
		agregaProductoPanel.add(txtCantidad_1);
		txtCantidad_1.setColumns(10);
		
		JLabel lblUnidad_1 = new JLabel("Unidad de medida");
		lblUnidad_1.setForeground(new Color(100, 100, 100));
		lblUnidad_1.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, lblUnidad_1, 25, SpringLayout.SOUTH, lblCantidad_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, lblUnidad_1, 0, SpringLayout.WEST, lblPropDatos_1);
		agregaProductoPanel.add(lblUnidad_1);
		
		txtUnidad_1 = new JTextField();
		txtUnidad_1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtUnidad_1.setBackground(new Color(225, 225, 225));
		txtUnidad_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(txtUnidad_1.getText().equals("Piezas, cajas, KG,..."))
				{
					txtUnidad_1.setText("");
					txtUnidad_1.setForeground(new Color(0, 0, 0));
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtUnidad_1.getText().equals(""))
				{
					txtUnidad_1.setText("Piezas, cajas, KG,...");
					txtUnidad_1.setForeground(new Color(140, 140, 140));
				}
			}
		});
		txtUnidad_1.setForeground(new Color(140, 140, 140));
		txtUnidad_1.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		txtUnidad_1.setText("Piezas, cajas, KG,...");
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, txtUnidad_1, -2, SpringLayout.NORTH, lblUnidad_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.SOUTH, txtUnidad_1, 2, SpringLayout.SOUTH, lblUnidad_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, txtUnidad_1, 0, SpringLayout.WEST, txtNomProduct_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.EAST, txtUnidad_1, 0, SpringLayout.EAST, txtNomProduct_1);
		agregaProductoPanel.add(txtUnidad_1);
		txtUnidad_1.setColumns(10);
		
		JLabel lblCostoCompra_1 = new JLabel("Costo (Unitario) - Compra");
		lblCostoCompra_1.setForeground(new Color(100, 100, 100));
		lblCostoCompra_1.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, lblCostoCompra_1, 0, SpringLayout.NORTH, lblNomProduct_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, lblCostoCompra_1, 112, SpringLayout.EAST, txtNomProduct_1);
		agregaProductoPanel.add(lblCostoCompra_1);
		
		txtCostoCompra_1 = new JTextField();
		txtCostoCompra_1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtCostoCompra_1.setBackground(new Color(225, 225, 225));
		txtCostoCompra_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtCostoCompra_1.getText().equals("0.00")) {
					txtCostoCompra_1.setForeground(new Color(0, 0, 0));
					txtCostoCompra_1.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtCostoCompra_1.getText().equals("")) {
					txtCostoCompra_1.setForeground(new Color(140, 140, 140));
					txtCostoCompra_1.setText("0.00");
				}
			}
		});
		txtCostoCompra_1.setText("0.00");
		txtCostoCompra_1.setForeground(new Color(140, 140, 140));
		txtCostoCompra_1.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, txtCostoCompra_1, -2, SpringLayout.NORTH, lblCostoCompra_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.SOUTH, txtCostoCompra_1, 2, SpringLayout.SOUTH, lblCostoCompra_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, txtCostoCompra_1, 50, SpringLayout.EAST, lblCostoCompra_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.EAST, txtCostoCompra_1, 300, SpringLayout.EAST, lblCostoCompra_1);
		agregaProductoPanel.add(txtCostoCompra_1);
		txtCostoCompra_1.setColumns(10);
		
		JLabel lblCostoCompraIcon_1 = new JLabel("$");
		lblCostoCompraIcon_1.setForeground(new Color(100, 100, 100));
		lblCostoCompraIcon_1.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_agregaProductoPanel.putConstraint(SpringLayout.SOUTH, lblCostoCompraIcon_1, 0, SpringLayout.SOUTH, lblCostoCompra_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, lblCostoCompraIcon_1, 0, SpringLayout.NORTH, lblCostoCompra_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.EAST, lblCostoCompraIcon_1, -5, SpringLayout.WEST, txtCostoCompra_1);
		agregaProductoPanel.add(lblCostoCompraIcon_1);
		
		JLabel lblPrecioVenta_1 = new JLabel("Precio (Unitario) - Venta");
		lblPrecioVenta_1.setForeground(new Color(100, 100, 100));
		lblPrecioVenta_1.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, lblPrecioVenta_1, 0, SpringLayout.NORTH, lblCodBar_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, lblPrecioVenta_1, 0, SpringLayout.WEST, lblCostoCompra_1);
		agregaProductoPanel.add(lblPrecioVenta_1);
		
		txtPrecioVenta_1 = new JTextField();
		txtPrecioVenta_1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtPrecioVenta_1.setBackground(new Color(225, 225, 225));
		txtPrecioVenta_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtPrecioVenta_1.getText().equals("0.00")) {
					txtPrecioVenta_1.setForeground(new Color(0, 0, 0));
					txtPrecioVenta_1.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtPrecioVenta_1.getText().equals("")) {
					txtPrecioVenta_1.setForeground(new Color(140, 140, 140));
					txtPrecioVenta_1.setText("0.00");
				}
			}
		});
		txtPrecioVenta_1.setForeground(new Color(140, 140, 140));
		txtPrecioVenta_1.setText("0.00");
		txtPrecioVenta_1.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, txtPrecioVenta_1, -2, SpringLayout.NORTH, lblPrecioVenta_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.SOUTH, txtPrecioVenta_1, 2, SpringLayout.SOUTH, lblPrecioVenta_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, txtPrecioVenta_1, 0, SpringLayout.WEST, txtCostoCompra_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.EAST, txtPrecioVenta_1, 0, SpringLayout.EAST, txtCostoCompra_1);
		agregaProductoPanel.add(txtPrecioVenta_1);
		txtPrecioVenta_1.setColumns(10);
		
		JLabel lblPrecioVentaIcon_1 = new JLabel("$");
		lblPrecioVentaIcon_1.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		lblPrecioVentaIcon_1.setForeground(new Color(100, 100, 100));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, lblPrecioVentaIcon_1, 0, SpringLayout.NORTH, lblCodBar_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.EAST, lblPrecioVentaIcon_1, 0, SpringLayout.EAST, lblCostoCompraIcon_1);
		agregaProductoPanel.add(lblPrecioVentaIcon_1);
		
		JLabel lblMinStock_1 = new JLabel("M\u00EDnimo Stock");
		lblMinStock_1.setForeground(new Color(100, 100, 100));
		lblMinStock_1.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, lblMinStock_1, 0, SpringLayout.NORTH, lblMarca_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, lblMinStock_1, 0, SpringLayout.WEST, lblPrecioVenta_1);
		agregaProductoPanel.add(lblMinStock_1);
		
		txtMinStock_1 = new JTextField();
		txtMinStock_1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtMinStock_1.setBackground(new Color(225, 225, 225));
		txtMinStock_1.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, txtMinStock_1, -2, SpringLayout.NORTH, lblMinStock_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.SOUTH, txtMinStock_1, 2, SpringLayout.SOUTH, lblMinStock_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, txtMinStock_1, 0, SpringLayout.WEST, txtPrecioVenta_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.EAST, txtMinStock_1, 0, SpringLayout.EAST, txtPrecioVenta_1);
		agregaProductoPanel.add(txtMinStock_1);
		txtMinStock_1.setColumns(10);
		
		JLabel lblMaxStock_1 = new JLabel("M\u00E1ximo Stock");
		lblMaxStock_1.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		lblMaxStock_1.setForeground(new Color(100, 100, 100));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, lblMaxStock_1, 0, SpringLayout.NORTH, lblCategoria_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, lblMaxStock_1, 0, SpringLayout.WEST, lblPrecioVenta_1);
		agregaProductoPanel.add(lblMaxStock_1);
		
		txtMaxStock_1 = new JTextField();
		txtMaxStock_1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtMaxStock_1.setBackground(new Color(225, 225, 225));
		txtMaxStock_1.setForeground(new Color(0, 0, 0));
		txtMaxStock_1.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, txtMaxStock_1, -2, SpringLayout.NORTH, lblMaxStock_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.SOUTH, txtMaxStock_1, 2, SpringLayout.SOUTH, lblMaxStock_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, txtMaxStock_1, 0, SpringLayout.WEST, txtPrecioVenta_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.EAST, txtMaxStock_1, 0, SpringLayout.EAST, txtPrecioVenta_1);
		agregaProductoPanel.add(txtMaxStock_1);
		txtMaxStock_1.setColumns(10);
		
		JLabel lblFechaEntrada_1 = new JLabel("Fecha de entrada");
		lblFechaEntrada_1.setForeground(new Color(100, 100, 100));
		lblFechaEntrada_1.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, lblFechaEntrada_1, 0, SpringLayout.NORTH, lblCantidad_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, lblFechaEntrada_1, 0, SpringLayout.WEST, lblPrecioVenta_1);
		agregaProductoPanel.add(lblFechaEntrada_1);
		
		txtFechaEntrada_1 = new JTextField();
		txtFechaEntrada_1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtFechaEntrada_1.setBackground(new Color(225, 225, 225));
		txtFechaEntrada_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtFechaEntrada_1.getText().equals("AAAA-MM-DD"))
				{
					txtFechaEntrada_1.setForeground(new Color(0, 0, 0));
					txtFechaEntrada_1.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtFechaEntrada_1.getText().equals(""))
				{
					txtFechaEntrada_1.setForeground(new Color(140, 140, 140));
					txtFechaEntrada_1.setText("AAAA-MM-DD");
				}
			}
		});
		txtFechaEntrada_1.setForeground(new Color(0, 0, 0));
		txtFechaEntrada_1.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, txtFechaEntrada_1, -2, SpringLayout.NORTH, lblFechaEntrada_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.SOUTH, txtFechaEntrada_1, 2, SpringLayout.SOUTH, lblFechaEntrada_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, txtFechaEntrada_1, 0, SpringLayout.WEST, txtPrecioVenta_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.EAST, txtFechaEntrada_1, 0, SpringLayout.EAST, txtPrecioVenta_1);
		agregaProductoPanel.add(txtFechaEntrada_1);
		txtFechaEntrada_1.setColumns(10);
		
		JLabel lblFechaCaducidad_1 = new JLabel("Fecha de caducidad");
		lblFechaCaducidad_1.setForeground(new Color(100, 100, 100));
		lblFechaCaducidad_1.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, lblFechaCaducidad_1, 0, SpringLayout.NORTH, lblUnidad_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, lblFechaCaducidad_1, 0, SpringLayout.WEST, lblCostoCompra_1);
		agregaProductoPanel.add(lblFechaCaducidad_1);
		
		txtFechaCaducidad_1 = new JTextField();
		txtFechaCaducidad_1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtFechaCaducidad_1.setBackground(new Color(225, 225, 225));
		txtFechaCaducidad_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtFechaCaducidad_1.getText().equals("AAAA-MM-DD")) {
					txtFechaCaducidad_1.setForeground(new Color(0, 0, 0));
					txtFechaCaducidad_1.setText("");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if(txtFechaCaducidad_1.getText().equals("")) {
					txtFechaCaducidad_1.setForeground(new Color(140, 140, 140));
					txtFechaCaducidad_1.setText("AAAA-MM-DD");
				}
			}
		});
		txtFechaCaducidad_1.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		txtFechaCaducidad_1.setText("AAAA-MM-DD");
		txtFechaCaducidad_1.setForeground(new Color(140, 140, 140));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, txtFechaCaducidad_1, -2, SpringLayout.NORTH, lblUnidad_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.SOUTH, txtFechaCaducidad_1, 2, SpringLayout.SOUTH, lblUnidad_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, txtFechaCaducidad_1, 0, SpringLayout.WEST, txtCostoCompra_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.EAST, txtFechaCaducidad_1, 0, SpringLayout.EAST, txtCostoCompra_1);
		agregaProductoPanel.add(txtFechaCaducidad_1);
		txtFechaCaducidad_1.setColumns(10);
		
		chckbxAGranel = new JCheckBox("Producto para venta a granel");
		chckbxAGranel.setForeground(new Color(100, 100, 100));
		chckbxAGranel.setFont(new Font("SF Pro Display", Font.PLAIN, 14));
		chckbxAGranel.setBackground(new Color(255, 255, 255));
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, chckbxAGranel, 0, SpringLayout.WEST, lblPropDatos_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, chckbxAGranel, 15, SpringLayout.SOUTH, lblUnidad_1);
		agregaProductoPanel.add(chckbxAGranel);
		
		final JLabel lblAdvertencia_1 = new JLabel("");
		lblAdvertencia_1.setForeground(new Color(255, 127, 80));
		lblAdvertencia_1.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 16));
		sl_agregaProductoPanel.putConstraint(SpringLayout.SOUTH, lblAdvertencia_1, -50, SpringLayout.SOUTH, agregaProductoPanel);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, lblAdvertencia_1, 0, SpringLayout.WEST, lblPropDatos_1);
		agregaProductoPanel.add(lblAdvertencia_1);
		
		final JPanel agregaPanel = new JPanel();
		agregaPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				agregaPanel.setBackground(new Color(204, 204, 204));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				agregaPanel.setBackground(new Color(140, 140, 140));
			}
			@Override
			public void mouseClicked(MouseEvent e) { //Método que verifica si los campos se han llenado correctamente para poder registrar un nuevo producto a la base de datos
				lblAdvertencia_1.setText("");
				//Valida que los campos estén llenos
				if(txtNomProduct_1.getText().equals("") || txtCodBar_1.getText().equals("") || txtMarca_1.getText().equals("") || txtCategoria_1.getText().equals("") || txtCantidad_1.getText().equals("") || txtUnidad_1.getText().equals("") || txtCostoCompra_1.getText().equals("") || txtPrecioVenta_1.getText().equals("") || txtMinStock_1.getText().equals("") || txtMaxStock_1.getText().equals("") || txtFechaEntrada_1.getText().equals("") || txtFechaCaducidad_1.getText().equals(""))
				{
					lblAdvertencia_1.setText("Asegúrate de haber llenado todos los campos correctamente");
				}
				else
				{
					String result = insertProduct();
					if(result.equals(""))
					{
						agregaPanel.setBackground(new Color(140, 140, 140));
						cleanAddProductPanel();
						card.show(menuProductosPanel, "2");
						DefaultTableModel model = (DefaultTableModel) tablaProductos.getModel();
						printWarehouse(model);
					}
					lblAdvertencia_1.setText(result);
				}
			}
		});
		agregaPanel.setBackground(new Color(140, 140, 140));
		sl_agregaProductoPanel.putConstraint(SpringLayout.NORTH, agregaPanel, -80, SpringLayout.SOUTH, agregaProductoPanel);
		sl_agregaProductoPanel.putConstraint(SpringLayout.SOUTH, agregaPanel, -50, SpringLayout.SOUTH, agregaProductoPanel);
		sl_agregaProductoPanel.putConstraint(SpringLayout.WEST, agregaPanel, 0, SpringLayout.WEST, txtPrecioVenta_1);
		sl_agregaProductoPanel.putConstraint(SpringLayout.EAST, agregaPanel, 0, SpringLayout.EAST, txtPrecioVenta_1);
		agregaProductoPanel.add(agregaPanel);
		SpringLayout sl_agregaPanel = new SpringLayout();
		agregaPanel.setLayout(sl_agregaPanel);
		
		JLabel lblAgrega = new JLabel("Agregar producto");
		lblAgrega.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 18));
		lblAgrega.setForeground(new Color(255, 255, 255));
		sl_agregaPanel.putConstraint(SpringLayout.SOUTH, lblAgrega, -5, SpringLayout.SOUTH, agregaPanel);
		sl_agregaPanel.putConstraint(SpringLayout.NORTH, lblAgrega, 5, SpringLayout.NORTH, agregaPanel);
		sl_agregaPanel.putConstraint(SpringLayout.EAST, lblAgrega, -48, SpringLayout.EAST, agregaPanel);
		sl_agregaPanel.putConstraint(SpringLayout.WEST, lblAgrega, 48, SpringLayout.WEST, agregaPanel);
		agregaPanel.add(lblAgrega);
		
		//-------------------Componentes del panel opciones, perteneciente al panel de PRODUCTOS--------------------------
		JLabel lblOpcion_1 = new JLabel("Accede al contenido de tu inventario, agrega y modifica productos, y m\u00E1s.");
		lblOpcion_1.setForeground(new Color(102, 102, 102));
		lblOpcion_1.setFont(new Font("SF Pro Display", Font.PLAIN, 18));
		sl_opcionesPanel.putConstraint(SpringLayout.NORTH, lblOpcion_1, 50, SpringLayout.NORTH, opcionesPanel);
		sl_opcionesPanel.putConstraint(SpringLayout.WEST, lblOpcion_1, 50, SpringLayout.WEST, opcionesPanel);
		opcionesPanel.add(lblOpcion_1);
		
		final JLabel lblBoton_1 = new JLabel("Lista de productos");
		lblBoton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblBoton_1.setForeground(new Color(51, 153, 255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblBoton_1.setForeground(new Color(51, 102, 204));
			}
			@Override
			public void mouseClicked(MouseEvent e) { //Método que revisa el inventario existente en la base de datos y crea una tabla donde imprime los resultados
				lblBoton_1.setForeground(new Color(51, 102, 204));
				card.show(menuProductosPanel, "2");
				DefaultTableModel model = (DefaultTableModel) tablaProductos.getModel();
				printWarehouse(model);
			}
		});
		lblBoton_1.setForeground(new Color(51, 102, 204));
		lblBoton_1.setFont(new Font("SF Pro Display", Font.PLAIN, 18));
		sl_opcionesPanel.putConstraint(SpringLayout.NORTH, lblBoton_1, 5, SpringLayout.SOUTH, lblOpcion_1);
		sl_opcionesPanel.putConstraint(SpringLayout.WEST, lblBoton_1, 0, SpringLayout.WEST, lblOpcion_1);
		opcionesPanel.add(lblBoton_1);
		
		JLabel lblOpcion_2 = new JLabel("Clasifica tus productos por categor\u00EDas para un manejo m\u00E1s sencillo.");
		lblOpcion_2.setForeground(new Color(102, 102, 102));
		lblOpcion_2.setFont(new Font("SF Pro Display", Font.PLAIN, 18));
		sl_opcionesPanel.putConstraint(SpringLayout.NORTH, lblOpcion_2, 50, SpringLayout.SOUTH, lblBoton_1);
		sl_opcionesPanel.putConstraint(SpringLayout.WEST, lblOpcion_2, 0, SpringLayout.WEST, lblOpcion_1);
		opcionesPanel.add(lblOpcion_2);
		
		final JLabel lblBotonl_2 = new JLabel("Categor\u00EDas");
		lblBotonl_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblBotonl_2.setForeground(new Color(51, 153, 255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblBotonl_2.setForeground(new Color(51, 102, 204));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				lblBotonl_2.setForeground(new Color(51, 102, 204));
				card.show(menuProductosPanel, "3");
				DefaultTableModel model = (DefaultTableModel) tablaCategorias.getModel();		//Obtenemos el modelo de la tabla de categorías
				printCategories(model);
			}
		});
		lblBotonl_2.setForeground(new Color(51, 102, 204));
		lblBotonl_2.setFont(new Font("SF Pro Display", Font.PLAIN, 18));
		sl_opcionesPanel.putConstraint(SpringLayout.NORTH, lblBotonl_2, 6, SpringLayout.SOUTH, lblOpcion_2);
		sl_opcionesPanel.putConstraint(SpringLayout.WEST, lblBotonl_2, 0, SpringLayout.WEST, lblOpcion_1);
		opcionesPanel.add(lblBotonl_2);
		
		JLabel lblOpcion_3 = new JLabel("Planifica tus pr\u00F3ximos surtidos.");
		lblOpcion_3.setForeground(new Color(102, 102, 102));
		lblOpcion_3.setFont(new Font("SF Pro Display", Font.PLAIN, 18));
		sl_opcionesPanel.putConstraint(SpringLayout.NORTH, lblOpcion_3, 50, SpringLayout.SOUTH, lblBotonl_2);
		sl_opcionesPanel.putConstraint(SpringLayout.WEST, lblOpcion_3, 0, SpringLayout.WEST, lblOpcion_1);
		opcionesPanel.add(lblOpcion_3);
		
		final JLabel lblBoton_3 = new JLabel("Pr\u00F3ximas compras");
		lblBoton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblBoton_3.setForeground(new Color(51, 153, 255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblBoton_3.setForeground(new Color(51, 102, 204));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				lblBoton_3.setForeground(new Color(51, 102, 204));
				//Removiendo todos los paneles
				/*menuProductosPanel.removeAll();
				menuProductosPanel.repaint();
				menuProductosPanel.revalidate();*/
				//Añadiendo panel de productos
				/*menuProductosPanel.add(proxComprasPanel);
				menuProductosPanel.repaint();
				menuProductosPanel.revalidate();*/
				card.show(menuProductosPanel, "4");
			}
		});
		lblBoton_3.setForeground(new Color(51, 102, 204));
		lblBoton_3.setFont(new Font("SF Pro Display", Font.PLAIN, 18));
		sl_opcionesPanel.putConstraint(SpringLayout.NORTH, lblBoton_3, 5, SpringLayout.SOUTH, lblOpcion_3);
		sl_opcionesPanel.putConstraint(SpringLayout.WEST, lblBoton_3, 0, SpringLayout.WEST, lblOpcion_1);
		opcionesPanel.add(lblBoton_3);
		
		
		
		//Panel de ventas. Muestra la venta que se está realizando actualmente---------------------------------------------------------------------
		final JPanel ventasPanel = new JPanel();
		ventasPanel.setBackground(new Color(225, 225, 225));
		sl_background.putConstraint(SpringLayout.NORTH, ventasPanel, 0, SpringLayout.NORTH, mainPanel);
		sl_background.putConstraint(SpringLayout.WEST, ventasPanel, 0, SpringLayout.EAST, mainPanel);
		sl_background.putConstraint(SpringLayout.EAST, ventasPanel, 0, SpringLayout.EAST, mainPanel);
		sl_background.putConstraint(SpringLayout.SOUTH, ventasPanel, 0, SpringLayout.SOUTH, mainPanel);
		//VentasPanel.setBackground(new Color(255, 255, 255));
		mainPanel.add(ventasPanel);
		SpringLayout sl_ventasPanel = new SpringLayout();
		ventasPanel.setLayout(sl_ventasPanel);		
		
		JLabel lblVentas_1 = new JLabel("Ventas");
		lblVentas_1.setFont(new Font("SF Pro Display", Font.PLAIN, 45));
		sl_ventasPanel.putConstraint(SpringLayout.WEST, lblVentas_1, 50, SpringLayout.WEST, ventasPanel);
		sl_ventasPanel.putConstraint(SpringLayout.NORTH, lblVentas_1, 30, SpringLayout.NORTH, ventasPanel);
		ventasPanel.add(lblVentas_1);
		
		JPanel tablaVentasPanel = new JPanel();
		sl_ventasPanel.putConstraint(SpringLayout.NORTH, tablaVentasPanel, 10, SpringLayout.SOUTH, lblVentas_1);
		sl_ventasPanel.putConstraint(SpringLayout.SOUTH, tablaVentasPanel, -50, SpringLayout.SOUTH, ventasPanel);
		sl_ventasPanel.putConstraint(SpringLayout.WEST, tablaVentasPanel, 50, SpringLayout.WEST, ventasPanel);
		sl_ventasPanel.putConstraint(SpringLayout.EAST, tablaVentasPanel, -50, SpringLayout.EAST, ventasPanel);
		tablaVentasPanel.setBorder(null);
		tablaVentasPanel.setBackground(Color.WHITE);
		ventasPanel.add(tablaVentasPanel);
		SpringLayout sl_tablaVentasPanel = new SpringLayout();
		tablaVentasPanel.setLayout(sl_tablaVentasPanel);
		
		tablaVentas = new JTable();
		tablaVentas.setBorder(null);
		tablaVentas.setFont(new Font("Futura Lt BT", Font.PLAIN, 14));
		tablaVentas.setBackground(new Color(255, 255, 255));
		sl_tablaVentasPanel.putConstraint(SpringLayout.NORTH, tablaVentas, 50, SpringLayout.NORTH, tablaVentasPanel);
		sl_tablaVentasPanel.putConstraint(SpringLayout.WEST, tablaVentas, 20, SpringLayout.WEST, tablaVentasPanel);
		sl_tablaVentasPanel.putConstraint(SpringLayout.EAST, tablaVentas, -20, SpringLayout.EAST, tablaVentasPanel);
		sl_tablaVentasPanel.putConstraint(SpringLayout.SOUTH, tablaVentas, -165, SpringLayout.SOUTH, tablaVentasPanel);
		tablaVentas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Producto", "Barcode", "Precio unit.", "Cantidad", "Total"
			}
			) {
				Class[] columnTypes = new Class[] {
					String.class, String.class, String.class, String.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
		tablaVentas.setGridColor(new Color(255, 255, 255));
		tablaVentas.setSelectionBackground(new Color(204, 204, 204));
		tablaVentas.setRowHeight(22);
		tablaVentasPanel.add(tablaVentas);
		
		JPanel HeaderPanel = new JPanel(new GridLayout(1,5));
		HeaderPanel.setBackground(Color.WHITE);
		sl_tablaVentasPanel.putConstraint(SpringLayout.NORTH, HeaderPanel, 10, SpringLayout.NORTH, tablaVentasPanel);
		sl_tablaVentasPanel.putConstraint(SpringLayout.WEST, HeaderPanel, 0, SpringLayout.WEST, tablaVentas);
		sl_tablaVentasPanel.putConstraint(SpringLayout.SOUTH, HeaderPanel, -5, SpringLayout.NORTH, tablaVentas);
		sl_tablaVentasPanel.putConstraint(SpringLayout.EAST, HeaderPanel, 0, SpringLayout.EAST, tablaVentas);
		tablaVentasPanel.add(HeaderPanel);
		
		JLabel lblHeadProducto = new JLabel("Producto");
		lblHeadProducto.setForeground(new Color(102, 102, 102));
		lblHeadProducto.setFont(new Font("SF Pro Display", Font.PLAIN, 14));
		HeaderPanel.add(lblHeadProducto);
		
		JLabel lblHeadBarcode = new JLabel("Código de barras");
		lblHeadBarcode.setForeground(new Color(102, 102, 102));
		lblHeadBarcode.setFont(new Font("SF Pro Display", Font.PLAIN, 14));
		HeaderPanel.add(lblHeadBarcode);
		
		JLabel lblHeadPrecio = new JLabel("Precio unitario");
		lblHeadPrecio.setForeground(new Color(102, 102, 102));
		lblHeadPrecio.setFont(new Font("SF Pro Display", Font.PLAIN, 14));
		HeaderPanel.add(lblHeadPrecio);
		
		JLabel lblHeadCantidad = new JLabel("Cantidad");
		lblHeadCantidad.setForeground(new Color(102, 102, 102));
		lblHeadCantidad.setFont(new Font("SF Pro Display", Font.PLAIN, 14));
		HeaderPanel.add(lblHeadCantidad);
		
		JLabel lblHeadTotal = new JLabel("Total");
		lblHeadTotal.setForeground(new Color(102, 102, 102));
		lblHeadTotal.setFont(new Font("SF Pro Display", Font.PLAIN, 14));
		HeaderPanel.add(lblHeadTotal);
		
		final JPanel borrarPanel = new JPanel();
		borrarPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				boolean band = true;
				SaleDetail sd = null;
				DefaultTableModel model = (DefaultTableModel)tablaVentas.getModel();
				try{
					Integer filaSelecIndice = tablaVentas.getSelectedRow();
					String barcode = (String)model.getValueAt(filaSelecIndice, 1);
					Product p = searchProduct(barcode);
					String q = (String)model.getValueAt(filaSelecIndice, 3);
					//ELIMINA LA UNIDAD DE MEDIDA DE LA CANTIDAD VENDIDA DE UN PRODUCTO
			    	StringBuilder sb = new StringBuilder();
			    	for(int i=0;i<q.length();i++)
			    	{
			    		if((q.charAt(i) >= '0' && q.charAt(i) <= '9') || q.charAt(i) == '.')
			    			sb.append(q.charAt(i));
			    	}
			    	String peso = sb.toString();
					Float quantity = Float.parseFloat(peso);
					sd = new SaleDetail(quantity, p, venta);
					ss.removeDetail(sd);
					model.removeRow(filaSelecIndice);
				}catch(Exception e)
					{
						band = false;
						try {
							JOptionPane.showMessageDialog(null, SystemMessages.getMessage("exception.1101"), "Advertencia", JOptionPane.WARNING_MESSAGE);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				if(tablaVentas.getRowCount() >= 0 && band == true && sd != null)
				{
					subtotal -= sd.getSubTotal();
					total -= sd.getSubTotal();
					txtSubtotal.setText(String.valueOf(subtotal)+"0");
					txtTotal.setText(String.valueOf(total)+"0");
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				borrarPanel.setBackground(new Color(204, 204, 204));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				borrarPanel.setBackground(new Color(140, 140, 140));
			}
		});
		borrarPanel.setBackground(new Color(140, 140, 140));
		sl_ventasPanel.putConstraint(SpringLayout.SOUTH, borrarPanel, -20, SpringLayout.NORTH, tablaVentasPanel);
		sl_ventasPanel.putConstraint(SpringLayout.WEST, borrarPanel, -250, SpringLayout.EAST, tablaVentasPanel);
		sl_ventasPanel.putConstraint(SpringLayout.EAST, borrarPanel, 0, SpringLayout.EAST, tablaVentasPanel);
		sl_ventasPanel.putConstraint(SpringLayout.NORTH, borrarPanel, -52, SpringLayout.NORTH, tablaVentasPanel);
		ventasPanel.add(borrarPanel);
		SpringLayout sl_borrarPanel = new SpringLayout();
		borrarPanel.setLayout(sl_borrarPanel);
		
		JLabel lblSubtotal = new JLabel("Subtotal:    $");
		sl_tablaVentasPanel.putConstraint(SpringLayout.NORTH, lblSubtotal, 15, SpringLayout.SOUTH, tablaVentas);
		sl_tablaVentasPanel.putConstraint(SpringLayout.EAST, lblSubtotal, -200, SpringLayout.EAST, tablaVentasPanel);
		sl_tablaVentasPanel.putConstraint(SpringLayout.WEST, lblSubtotal, -307, SpringLayout.EAST, tablaVentasPanel);
		lblSubtotal.setForeground(new Color(51, 51, 51));
		lblSubtotal.setFont(new Font("Avenir LT Std 45 Book", Font.PLAIN, 18));
		tablaVentasPanel.add(lblSubtotal);
		
		JLabel lblDescuento = new JLabel("Descuento:    $");
		sl_tablaVentasPanel.putConstraint(SpringLayout.NORTH, lblDescuento, 5, SpringLayout.SOUTH, lblSubtotal);
		sl_tablaVentasPanel.putConstraint(SpringLayout.EAST, lblDescuento, -200, SpringLayout.EAST, tablaVentasPanel);
		sl_tablaVentasPanel.putConstraint(SpringLayout.WEST, lblDescuento, -327, SpringLayout.EAST, tablaVentasPanel);
		lblDescuento.setForeground(new Color(51, 51, 51));
		lblDescuento.setFont(new Font("Avenir LT Std 45 Book", Font.PLAIN, 18));
		tablaVentasPanel.add(lblDescuento);
		
		JLabel lblTotal = new JLabel("Total:  $");
		lblTotal.setForeground(new Color(204, 51, 51));
		sl_tablaVentasPanel.putConstraint(SpringLayout.NORTH, lblTotal, 5, SpringLayout.SOUTH, lblDescuento);
		sl_tablaVentasPanel.putConstraint(SpringLayout.EAST, lblTotal, -200, SpringLayout.EAST, tablaVentasPanel);
		sl_tablaVentasPanel.putConstraint(SpringLayout.WEST, lblTotal, -310, SpringLayout.EAST, tablaVentasPanel);
		lblTotal.setFont(new Font("Avenir LT Std 65 Medium", Font.PLAIN, 30));
		tablaVentasPanel.add(lblTotal);
		
		txtSubtotal = new JTextField();
		txtDescuento = new JTextField();
		txtTotal = new JTextField();
		
		//txtSubtotal = new JTextField();
		txtSubtotal.setText("0.00");
		txtSubtotal.setForeground(new Color(51, 51, 51));
		txtSubtotal.setBackground(Color.WHITE);
		txtSubtotal.setFont(new Font("SF Pro Display", Font.PLAIN, 18));
		txtSubtotal.setEditable(false);
		sl_tablaVentasPanel.putConstraint(SpringLayout.NORTH, txtSubtotal, 0, SpringLayout.NORTH, lblSubtotal);
		sl_tablaVentasPanel.putConstraint(SpringLayout.SOUTH, txtSubtotal, 0, SpringLayout.SOUTH, lblSubtotal);
		sl_tablaVentasPanel.putConstraint(SpringLayout.WEST, txtSubtotal, 0, SpringLayout.EAST, lblSubtotal);
		sl_tablaVentasPanel.putConstraint(SpringLayout.EAST, txtSubtotal, 0, SpringLayout.EAST, tablaVentas);
		txtSubtotal.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		tablaVentasPanel.add(txtSubtotal);
		txtSubtotal.setColumns(10);
		
		//txtDescuento = new JTextField();
		txtDescuento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!txtDescuento.getText().equals("0") && !txtDescuento.getText().equals("0.0") && !txtDescuento.getText().equals("0.00") && !txtDescuento.getText().equals(".00"))
				{
					descuento = Double.parseDouble(txtDescuento.getText());
					if(descuento >= subtotal)
					{
						JOptionPane.showMessageDialog(null, "El descuento excede el precio de compra. \nAsegúrate de colocar un descuento menor que el subtotal de compra.", "Advertencia", JOptionPane.ERROR_MESSAGE);
						descuento = 0.0;
						txtDescuento.setText(String.format("%.2f", descuento));
						total = subtotal;
						txtTotal.setText(String.format("%.2f", total));
					}
					else
					{
						String discount = JOptionPane.showInputDialog(null,"Descuento de $"+txtDescuento.getText()+"\n\n¿Desea cambiar la cantidad? (Sin $)",JOptionPane.QUESTION_MESSAGE);
						descuento = Double.parseDouble(discount);
						txtDescuento.setText(String.format(" %.2f", descuento));
						total = subtotal - descuento;
						txtTotal.setText(String.format("%.2f", total));
					}
				}
				else
				{
					total = subtotal;
					txtTotal.setText(String.format("%.2f", total));
				}
			}
		});
		txtDescuento.setForeground(new Color(51, 51, 51));
		txtDescuento.setText("0.00");
		txtDescuento.setFont(new Font("SF Pro Display", Font.PLAIN, 18));
		txtDescuento.setBackground(Color.WHITE);
		sl_tablaVentasPanel.putConstraint(SpringLayout.NORTH, txtDescuento, 5, SpringLayout.SOUTH, txtSubtotal);
		sl_tablaVentasPanel.putConstraint(SpringLayout.SOUTH, txtDescuento, 0, SpringLayout.SOUTH, lblDescuento);
		sl_tablaVentasPanel.putConstraint(SpringLayout.WEST, txtDescuento, 0, SpringLayout.EAST, lblDescuento);
		sl_tablaVentasPanel.putConstraint(SpringLayout.EAST, txtDescuento, 0, SpringLayout.EAST, tablaVentas);
		txtDescuento.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		tablaVentasPanel.add(txtDescuento);
		txtDescuento.setColumns(10);
		
		//txtTotal = new JTextField();
		txtTotal.setForeground(new Color(204, 51, 51));
		txtTotal.setText("0.00");
		txtTotal.setEditable(false);
		txtTotal.setFont(new Font("SF Pro Display", Font.PLAIN, 30));
		txtTotal.setBackground(Color.WHITE);
		sl_tablaVentasPanel.putConstraint(SpringLayout.NORTH, txtTotal, 3, SpringLayout.SOUTH, txtDescuento);
		sl_tablaVentasPanel.putConstraint(SpringLayout.SOUTH, txtTotal, 0, SpringLayout.SOUTH, lblTotal);
		sl_tablaVentasPanel.putConstraint(SpringLayout.WEST, txtTotal, 2, SpringLayout.EAST, lblTotal);
		sl_tablaVentasPanel.putConstraint(SpringLayout.EAST, txtTotal, 0, SpringLayout.EAST, tablaVentas);
		txtTotal.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		tablaVentasPanel.add(txtTotal);
		txtTotal.setColumns(10);
		
		final JPanel aceptarPanel = new JPanel();
		aceptarPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				aceptarPanel.setBackground(new Color(102, 255, 153));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				aceptarPanel.setBackground(new Color(0, 204, 102));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel)tablaVentas.getModel();
				if(venta != null && model.getRowCount() > 0)
				{
					Double pago;
					do {
						String money = JOptionPane.showInputDialog(null, "Efectivo recibido: ", "Pago de la compra", JOptionPane.QUESTION_MESSAGE);
						pago = Double.parseDouble(money);
						if(pago < total)
							JOptionPane.showMessageDialog(null, "Cantidad insuficiente de efectivo", "Pago no cubierto", JOptionPane.ERROR_MESSAGE);
					} while(pago < total);
					Double cambio = pago - total;
					if(model.getRowCount() > 0)
					{
						Vector<Vector> data = new Vector<Vector>();
						while(model.getRowCount() > 0)
						{
							Vector<String> row = new Vector<String>();
							row.add((String)model.getValueAt(0, 0));
							row.add((String)model.getValueAt(0, 2));
							row.add((String)model.getValueAt(0, 3));
							row.add((String)model.getValueAt(0, 4));
							data.add(row);
						    model.removeRow(0);
						}
						JOptionPane.showMessageDialog(null, "Imprimiendo ticket de compra."
															+"\nSubtotal:         $"+subtotal
															+"\nDescuento:   $"+descuento
															+"\nImporte:           $"+total
															+"\nPAGO:             $"+pago
															+"\nCAMBIO:        $"+cambio, 
															"Venta realizada",  JOptionPane.PLAIN_MESSAGE);
						printer p = new printer();
						p.print(data, txtSubtotal.getText(), txtDescuento.getText(), txtTotal.getText(), String.format("%.2f", pago), String.format("%.2f", cambio));
						subtotal = 0.0;
						total = 0.0;
						descuento = 0.0;
						txtSubtotal.setText(String.format("%.2f", subtotal));
						txtDescuento.setText(String.format("%.2f", descuento));
						txtTotal.setText(String.format("%.2f", total));
						ss.addSale(venta);
						ss.openSale();
					}
					else
						JOptionPane.showMessageDialog(null, "No has agregado artículos  la venta actual", "Agregue artículos",  JOptionPane.PLAIN_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(null, "Comienza una venta antes de aceptar", "Agregue artículos",  JOptionPane.PLAIN_MESSAGE);
			}
		});
		aceptarPanel.setBackground(new Color(0, 204, 102));
		sl_tablaVentasPanel.putConstraint(SpringLayout.NORTH, aceptarPanel, -60, SpringLayout.SOUTH, tablaVentasPanel);
		sl_tablaVentasPanel.putConstraint(SpringLayout.EAST, aceptarPanel, 0, SpringLayout.EAST, tablaVentas);
		sl_tablaVentasPanel.putConstraint(SpringLayout.WEST, aceptarPanel, 70, SpringLayout.WEST, lblTotal);
		sl_tablaVentasPanel.putConstraint(SpringLayout.SOUTH, aceptarPanel, -25, SpringLayout.SOUTH, tablaVentasPanel);
		SpringLayout sl_aceptarPanel = new SpringLayout();
		aceptarPanel.setLayout(sl_aceptarPanel);
		tablaVentasPanel.add(aceptarPanel);
		
		final JPanel cancelarPanel = new JPanel();
		cancelarPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				cancelarPanel.setBackground(new Color(255, 117, 86));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				cancelarPanel.setBackground(new Color(255, 51, 51));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel)tablaVentas.getModel();
				if(venta != null && model.getRowCount() > 0) 
				{
					Integer opcion;
					do {
						String opc = JOptionPane.showInputDialog(null, "¿Estás seguro de cancelar la venta? \n1 = Sí \n2 = No");
						opcion = Integer.parseInt(opc);
						if(opcion != 1 && opcion != 2)
							JOptionPane.showMessageDialog(null, "Proporciona una opción válida", "Opción inválida", JOptionPane.ERROR_MESSAGE);
					} while(opcion != 1 && opcion != 2);
					if(opcion == 1)
					{
						cleanTable(model);
						ss.cancelSale();
						subtotal = 0.0;
						descuento = 0.0;
						total = 0.0;
						txtSubtotal.setText(String.format("%.2f", subtotal));
						txtDescuento.setText(String.format("%.2f", descuento));
						txtTotal.setText(String.format("%.2f", total));
					}
				}
			}
		});
		cancelarPanel.setBackground(new Color(255, 51, 51));
		sl_tablaVentasPanel.putConstraint(SpringLayout.NORTH, cancelarPanel, 0, SpringLayout.NORTH, aceptarPanel);
		sl_tablaVentasPanel.putConstraint(SpringLayout.EAST, cancelarPanel, -20, SpringLayout.WEST, aceptarPanel);
		sl_tablaVentasPanel.putConstraint(SpringLayout.WEST, cancelarPanel, -225, SpringLayout.WEST, aceptarPanel);
		sl_tablaVentasPanel.putConstraint(SpringLayout.SOUTH, cancelarPanel, 0, SpringLayout.SOUTH, aceptarPanel);		
		SpringLayout sl_cancelarPanel = new SpringLayout();
		cancelarPanel.setLayout(sl_cancelarPanel);
		tablaVentasPanel.add(cancelarPanel);
		
		JLabel lblCancelar = new JLabel("CANCELAR");
		lblCancelar.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 20));
		lblCancelar.setForeground(new Color(255, 255, 255));
		sl_cancelarPanel.putConstraint(SpringLayout.NORTH, lblCancelar, 8, SpringLayout.NORTH, cancelarPanel);
		sl_cancelarPanel.putConstraint(SpringLayout.SOUTH, lblCancelar, -8, SpringLayout.SOUTH, cancelarPanel);
		sl_cancelarPanel.putConstraint(SpringLayout.WEST, lblCancelar, 50, SpringLayout.WEST, cancelarPanel);
		sl_cancelarPanel.putConstraint(SpringLayout.EAST, lblCancelar, -50, SpringLayout.EAST, cancelarPanel);
		cancelarPanel.add(lblCancelar);
		
		JLabel lblAceptar = new JLabel("ACEPTAR");
		lblAceptar.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 20));
		lblAceptar.setForeground(new Color(255, 255, 255));
		sl_aceptarPanel.putConstraint(SpringLayout.NORTH, lblAceptar, 8, SpringLayout.NORTH, aceptarPanel);
		sl_aceptarPanel.putConstraint(SpringLayout.SOUTH, lblAceptar, -8, SpringLayout.SOUTH, aceptarPanel);
		sl_aceptarPanel.putConstraint(SpringLayout.WEST, lblAceptar, 62, SpringLayout.WEST, aceptarPanel);
		sl_aceptarPanel.putConstraint(SpringLayout.EAST, lblAceptar, -62, SpringLayout.EAST, aceptarPanel);
		aceptarPanel.add(lblAceptar);
		
		txtCodigoBar = new JTextField();
		JLabel lblCodigoBar = new JLabel("Nombre del producto / C\u00F3digo de barras");
		JLabel lblCantProduct = new JLabel("Cantidad (piezas, kG, cajas, etc.)");
		txtCantidad = new JTextField();
		
		//txtCodigoBar = new JTextField();
		sl_tablaVentasPanel.putConstraint(SpringLayout.NORTH, txtCodigoBar, 10, SpringLayout.NORTH, lblSubtotal);
		sl_tablaVentasPanel.putConstraint(SpringLayout.EAST, txtCodigoBar, -200, SpringLayout.WEST, lblTotal);
		sl_tablaVentasPanel.putConstraint(SpringLayout.SOUTH, txtCodigoBar, 13, SpringLayout.SOUTH, lblSubtotal);
		txtCodigoBar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txtCodigoBar.getText().equals(""))
				{
					// TODO Auto-generated method stub
					sellProducts(txtCodigoBar.getText());
					
				}
			}});
		txtCodigoBar.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		txtCodigoBar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtCodigoBar.setBackground(new Color(220, 220, 220));
		tablaVentasPanel.add(txtCodigoBar);
		txtCodigoBar.setColumns(10);
		
		//JLabel lblCodigoBar = new JLabel("C\u00F3digo de barras");
		sl_tablaVentasPanel.putConstraint(SpringLayout.WEST, txtCodigoBar, 20, SpringLayout.EAST, lblCodigoBar);
		sl_tablaVentasPanel.putConstraint(SpringLayout.SOUTH, lblCodigoBar, 0, SpringLayout.SOUTH, txtCodigoBar);
		sl_tablaVentasPanel.putConstraint(SpringLayout.WEST, lblCodigoBar, 0, SpringLayout.WEST, tablaVentas);
		lblCodigoBar.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		tablaVentasPanel.add(lblCodigoBar);
		
		//JLabel lblCantidad = new JLabel("Cantidad");
		lblCantProduct.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		sl_tablaVentasPanel.putConstraint(SpringLayout.NORTH, lblCantProduct, 10, SpringLayout.SOUTH, lblCodigoBar);
		sl_tablaVentasPanel.putConstraint(SpringLayout.EAST, lblCantProduct, 0, SpringLayout.EAST, lblCodigoBar);
		tablaVentasPanel.add(lblCantProduct);
		
		//txtCantidad = new JTextField();
		txtCantidad.setFont(new Font("Futura Lt BT", Font.PLAIN, 16));
		txtCantidad.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtCantidad.setBackground(new Color(220, 220, 220));
		sl_tablaVentasPanel.putConstraint(SpringLayout.NORTH, txtCantidad, 10, SpringLayout.SOUTH, txtCodigoBar);
		sl_tablaVentasPanel.putConstraint(SpringLayout.WEST, txtCantidad, 0, SpringLayout.WEST, txtCodigoBar);
		sl_tablaVentasPanel.putConstraint(SpringLayout.EAST, txtCantidad, 0, SpringLayout.EAST, txtCodigoBar);
		sl_tablaVentasPanel.putConstraint(SpringLayout.SOUTH, txtCantidad, 1, SpringLayout.SOUTH, lblCantProduct);
		txtCantidad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!txtCodigoBar.getText().equals(""))
				{
					if(txtCantidad.getText().toLowerCase().equals("p"))
					{
						Product p = searchProduct(txtCodigoBar.getText());
						if(p != null && p.isInBulk())
						{
							txtCantidad.setText(String.format("%.2f", scale.weigh()));
						}
					}
					else
						sellProducts(txtCodigoBar.getText());
				}
			}
		});
		tablaVentasPanel.add(txtCantidad);
		txtCantidad.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBackground(new Color(204, 204, 204));
		separator_1.setForeground(new Color(204, 204, 204));
		sl_tablaVentasPanel.putConstraint(SpringLayout.SOUTH, separator_1, 0, SpringLayout.NORTH, tablaVentas);
		sl_tablaVentasPanel.putConstraint(SpringLayout.WEST, separator_1, 0, SpringLayout.WEST, tablaVentas);
		sl_tablaVentasPanel.putConstraint(SpringLayout.EAST, separator_1, 0, SpringLayout.EAST, tablaVentas);
		tablaVentasPanel.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBackground(new Color(204, 204, 204));
		separator_2.setForeground(new Color(204, 204, 204));
		sl_tablaVentasPanel.putConstraint(SpringLayout.NORTH, separator_2, 0, SpringLayout.SOUTH, tablaVentas);
		sl_tablaVentasPanel.putConstraint(SpringLayout.WEST, separator_2, 0, SpringLayout.WEST, tablaVentas);
		sl_tablaVentasPanel.putConstraint(SpringLayout.EAST, separator_2, 0, SpringLayout.EAST, tablaVentas);
		tablaVentasPanel.add(separator_2);
		
		final JPanel pesarPanel = new JPanel();
		pesarPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				pesarPanel.setBackground(new Color(204, 204, 204));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				pesarPanel.setBackground(new Color(140, 140, 140));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!txtCodigoBar.getText().equals(""))
				{
					Product p = searchProduct(txtCodigoBar.getText());
					if(p != null)
					{
						if(p.isInBulk())
							txtCantidad.setText(String.format("%.2f", scale.weigh()));
						else
							JOptionPane.showMessageDialog(null, "Este producto no está disponible\npara su venta a granel\n", "Producto no a granel", JOptionPane.ERROR_MESSAGE);
					}
					else
						JOptionPane.showMessageDialog(null, "Este producto no se encuentra en su inventario\n", "Producto no encontrado", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		pesarPanel.setBorder(null);
		pesarPanel.setBackground(new Color(140, 140, 140));
		sl_tablaVentasPanel.putConstraint(SpringLayout.NORTH, pesarPanel, 0, SpringLayout.NORTH, cancelarPanel);
		sl_tablaVentasPanel.putConstraint(SpringLayout.SOUTH, pesarPanel, 0, SpringLayout.SOUTH, cancelarPanel);
		sl_tablaVentasPanel.putConstraint(SpringLayout.WEST, pesarPanel, -205, SpringLayout.EAST, txtCodigoBar);
		sl_tablaVentasPanel.putConstraint(SpringLayout.EAST, pesarPanel, 0, SpringLayout.EAST, txtCodigoBar);
		tablaVentasPanel.add(pesarPanel);
		SpringLayout sl_pesarPanel = new SpringLayout();
		pesarPanel.setLayout(sl_pesarPanel);
		
		JLabel lblPesar = new JLabel("PESAR");
		lblPesar.setForeground(new Color(255, 255, 255));
		lblPesar.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 20));
		sl_pesarPanel.putConstraint(SpringLayout.NORTH, lblPesar, 8, SpringLayout.NORTH, pesarPanel);
		sl_pesarPanel.putConstraint(SpringLayout.SOUTH, lblPesar, -8, SpringLayout.SOUTH, pesarPanel);
		sl_pesarPanel.putConstraint(SpringLayout.WEST, lblPesar, 70, SpringLayout.WEST, pesarPanel);
		sl_pesarPanel.putConstraint(SpringLayout.EAST, lblPesar, -70, SpringLayout.EAST, pesarPanel);
		pesarPanel.add(lblPesar);
		
		
		JLabel lblBorrarProducto = new JLabel("Borrar producto");
		sl_borrarPanel.putConstraint(SpringLayout.NORTH, lblBorrarProducto, 5, SpringLayout.NORTH, borrarPanel);
		sl_borrarPanel.putConstraint(SpringLayout.WEST, lblBorrarProducto, 55, SpringLayout.WEST, borrarPanel);
		sl_borrarPanel.putConstraint(SpringLayout.SOUTH, lblBorrarProducto, -5, SpringLayout.SOUTH, borrarPanel);
		sl_borrarPanel.putConstraint(SpringLayout.EAST, lblBorrarProducto, -55, SpringLayout.EAST, borrarPanel);
		lblBorrarProducto.setForeground(new Color(255, 255, 255));
		lblBorrarProducto.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 18));
		borrarPanel.add(lblBorrarProducto);
		
		//Panel de historial. Muestra el historial de ventas y de compras realizadas por el negocio.
		final JPanel historialPanel = new JPanel();
		sl_background.putConstraint(SpringLayout.NORTH, historialPanel, 0, SpringLayout.NORTH, mainPanel);
		sl_background.putConstraint(SpringLayout.WEST, historialPanel, 0, SpringLayout.EAST, mainPanel);
		sl_background.putConstraint(SpringLayout.EAST, historialPanel, 0, SpringLayout.EAST, mainPanel);
		sl_background.putConstraint(SpringLayout.SOUTH, historialPanel, 0, SpringLayout.SOUTH, mainPanel);
		historialPanel.setBackground(new Color(225, 225, 225));
		mainPanel.add(historialPanel);
		SpringLayout sl_historialPanel = new SpringLayout();
		historialPanel.setLayout(sl_historialPanel);
		
		JLabel lblHistorial_1 = new JLabel("Historial");
		sl_historialPanel.putConstraint(SpringLayout.NORTH, lblHistorial_1, 30, SpringLayout.NORTH, historialPanel);
		sl_historialPanel.putConstraint(SpringLayout.WEST, lblHistorial_1, 50, SpringLayout.WEST, historialPanel);
		lblHistorial_1.setFont(new Font("SF Pro Display", Font.PLAIN, 45));
		historialPanel.add(lblHistorial_1);
		
		JPanel ListaHistorialPanel = new JPanel();
		sl_historialPanel.putConstraint(SpringLayout.NORTH, ListaHistorialPanel, 10, SpringLayout.SOUTH, lblHistorial_1);
		sl_historialPanel.putConstraint(SpringLayout.WEST, ListaHistorialPanel, 0, SpringLayout.WEST, lblHistorial_1);
		sl_historialPanel.putConstraint(SpringLayout.EAST, ListaHistorialPanel, -50, SpringLayout.EAST, historialPanel);
		sl_historialPanel.putConstraint(SpringLayout.SOUTH, ListaHistorialPanel, -50, SpringLayout.SOUTH, historialPanel);
		ListaHistorialPanel.setBorder(null);
		ListaHistorialPanel.setBackground(Color.WHITE);
		historialPanel.add(ListaHistorialPanel);
		SpringLayout sl_ListaHistorialPanel = new SpringLayout();
		ListaHistorialPanel.setLayout(sl_ListaHistorialPanel);
		
		JLabel lblNewLabel = new JLabel("No has realizado ninguna venta. Ve a la opción 'Ventas' para comenzar a vender.");
		lblNewLabel.setForeground(new Color(102, 102, 102));
		lblNewLabel.setFont(new Font("SF Pro Display", Font.PLAIN, 20));
		sl_ListaHistorialPanel.putConstraint(SpringLayout.NORTH, lblNewLabel, 20, SpringLayout.NORTH, ListaHistorialPanel);
		sl_ListaHistorialPanel.putConstraint(SpringLayout.WEST, lblNewLabel, 20, SpringLayout.WEST, ListaHistorialPanel);
		ListaHistorialPanel.add(lblNewLabel);
		
		//---------------------Panel de balance. Muestralas estadísticas del negocio., tales como ganancias, pérdidas, etc.--------------------
		final JPanel balancePanel = new JPanel();
		balancePanel.setBackground(new Color(225, 225, 225));
		sl_background.putConstraint(SpringLayout.NORTH, balancePanel, 0, SpringLayout.NORTH, mainPanel);
		sl_background.putConstraint(SpringLayout.WEST, balancePanel, 0, SpringLayout.EAST, mainPanel);
		sl_background.putConstraint(SpringLayout.EAST, balancePanel, 0, SpringLayout.EAST, mainPanel);
		sl_background.putConstraint(SpringLayout.SOUTH, balancePanel, 0, SpringLayout.SOUTH, mainPanel);
		mainPanel.add(balancePanel);
		SpringLayout sl_balancePanel = new SpringLayout();
		balancePanel.setLayout(sl_balancePanel);
		
		JLabel lblBlance_1 = new JLabel("Balance");
		sl_balancePanel.putConstraint(SpringLayout.NORTH, lblBlance_1, 30, SpringLayout.NORTH, balancePanel);
		sl_balancePanel.putConstraint(SpringLayout.WEST, lblBlance_1, 50, SpringLayout.WEST, balancePanel);
		lblBlance_1.setFont(new Font("SF Pro Display", Font.PLAIN, 45));
		balancePanel.add(lblBlance_1);
		
		JPanel TablaBalancePanel = new JPanel();
		sl_balancePanel.putConstraint(SpringLayout.NORTH, TablaBalancePanel, 10, SpringLayout.SOUTH, lblBlance_1);
		sl_balancePanel.putConstraint(SpringLayout.WEST, TablaBalancePanel, 0, SpringLayout.WEST, lblBlance_1);
		sl_balancePanel.putConstraint(SpringLayout.EAST, TablaBalancePanel, -50, SpringLayout.EAST, balancePanel);
		sl_balancePanel.putConstraint(SpringLayout.SOUTH, TablaBalancePanel, -50, SpringLayout.SOUTH, balancePanel);
		TablaBalancePanel.setBorder(null);
		TablaBalancePanel.setBackground(Color.WHITE);
		balancePanel.add(TablaBalancePanel);
		SpringLayout sl_TablaBalancePanel = new SpringLayout();
		TablaBalancePanel.setLayout(sl_TablaBalancePanel);
		
		JLabel lblLogo = new JLabel("POS");
		sl_LogoPanel.putConstraint(SpringLayout.WEST, lblLogo, 30, SpringLayout.WEST, LogoPanel);
		sl_LogoPanel.putConstraint(SpringLayout.SOUTH, lblLogo, -13, SpringLayout.SOUTH, LogoPanel);
		lblLogo.setForeground(new Color(255, 255, 255));
		lblLogo.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 40));
		LogoPanel.add(lblLogo);
		
		final JLabel lblInicio = new JLabel("Inicio");
		lblInicio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblInicio.setForeground(new Color(60, 169, 113));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblInicio.setForeground(new Color(204, 204, 204));
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//Removiendo todos los paneles
				mainPanel.removeAll();
				mainPanel.repaint();
				mainPanel.revalidate();
				//Añadiendo panel de inicio
				mainPanel.add(inicioPanel);
				mainPanel.repaint();
				mainPanel.revalidate();
			}
		});
		
		JLabel lblInicioIcon = new JLabel("");
		sl_menuPanel.putConstraint(SpringLayout.SOUTH, lblInicioIcon, 0, SpringLayout.SOUTH, lblInicio);
		sl_menuPanel.putConstraint(SpringLayout.EAST, lblInicioIcon, -10, SpringLayout.WEST, lblInicio);
		lblInicioIcon.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/home.png")));
		menuPanel.add(lblInicioIcon);
		
		JLabel lblProductosIcon = new JLabel("");
		sl_menuPanel.putConstraint(SpringLayout.WEST, lblProductosIcon, 0, SpringLayout.WEST, lblInicioIcon);
		lblProductosIcon.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/products.png")));
		menuPanel.add(lblProductosIcon);
		
		JLabel lblVentasIcon = new JLabel("");
		sl_menuPanel.putConstraint(SpringLayout.WEST, lblVentasIcon, 0, SpringLayout.WEST, lblInicioIcon);
		lblVentasIcon.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/sell.png")));
		menuPanel.add(lblVentasIcon);
		
		JLabel lblHistorialIcon = new JLabel("");
		sl_menuPanel.putConstraint(SpringLayout.WEST, lblHistorialIcon, 0, SpringLayout.WEST, lblInicioIcon);
		lblHistorialIcon.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/history.png")));
		menuPanel.add(lblHistorialIcon);
		
		JLabel lblBalanceIcon = new JLabel("");
		sl_menuPanel.putConstraint(SpringLayout.WEST, lblBalanceIcon, 0, SpringLayout.WEST, lblInicioIcon);
		lblBalanceIcon.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/sales.png")));
		menuPanel.add(lblBalanceIcon);
		sl_menuPanel.putConstraint(SpringLayout.WEST, lblInicio, 50, SpringLayout.WEST, menuPanel);
		lblInicio.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 18));
		lblInicio.setForeground(new Color(204, 204, 204));
		menuPanel.add(lblInicio);
		
		final JLabel lblProducto = new JLabel("Productos");
		sl_menuPanel.putConstraint(SpringLayout.SOUTH, lblProductosIcon, 0, SpringLayout.SOUTH, lblProducto);
		sl_menuPanel.putConstraint(SpringLayout.NORTH, lblProducto, 31, SpringLayout.SOUTH, lblInicio);
		sl_menuPanel.putConstraint(SpringLayout.WEST, lblProducto, 0, SpringLayout.WEST, lblInicio);
		lblProducto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblProducto.setForeground(new Color(60, 169, 113));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblProducto.setForeground(new Color(204, 204, 204));
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//Removiendo todos los paneles
				mainPanel.removeAll();
				mainPanel.repaint();
				mainPanel.revalidate();
				//Añadiendo panel de productos
				mainPanel.add(productosPanel);
				mainPanel.repaint();
				mainPanel.revalidate();
			}
		});
		lblProducto.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 18));
		lblProducto.setForeground(new Color(204, 204, 204));
		menuPanel.add(lblProducto);
		
		final JLabel lblVentas = new JLabel("Ventas");
		lblVentas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblVentas.setForeground(new Color(60, 169, 113));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblVentas.setForeground(new Color(204, 204, 204));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				//Removiendo todos los paneles
				mainPanel.removeAll();
				mainPanel.repaint();
				mainPanel.revalidate();
				//Añadiendo panel de ventas
				mainPanel.add(ventasPanel);
				mainPanel.repaint();
				mainPanel.revalidate();
				//Crea una venta
				openSale();
			}
		});
		sl_menuPanel.putConstraint(SpringLayout.SOUTH, lblVentasIcon, 0, SpringLayout.SOUTH, lblVentas);
		sl_menuPanel.putConstraint(SpringLayout.SOUTH, lblInicio, -81, SpringLayout.NORTH, lblVentas);
		sl_menuPanel.putConstraint(SpringLayout.WEST, lblVentas, 0, SpringLayout.WEST, lblInicio);
		lblVentas.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 18));
		lblVentas.setForeground(new Color(204, 204, 204));
		menuPanel.add(lblVentas);
		
		final JLabel lblHistorial = new JLabel("Historial");
		lblHistorial.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblHistorial.setForeground(new Color(60, 169, 113));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblHistorial.setForeground(new Color(204, 204, 204));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				//Removiendo todos los paneles
				mainPanel.removeAll();
				mainPanel.repaint();
				mainPanel.revalidate();
				//Añadiendo panel de historial
				mainPanel.add(historialPanel);
				mainPanel.repaint();
				mainPanel.revalidate();
			}
		});
		sl_menuPanel.putConstraint(SpringLayout.SOUTH, lblHistorialIcon, 0, SpringLayout.SOUTH, lblHistorial);
		sl_menuPanel.putConstraint(SpringLayout.SOUTH, lblVentas, -31, SpringLayout.NORTH, lblHistorial);
		sl_menuPanel.putConstraint(SpringLayout.WEST, lblHistorial, 0, SpringLayout.WEST, lblInicio);
		lblHistorial.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 18));
		lblHistorial.setForeground(new Color(204, 204, 204));
		menuPanel.add(lblHistorial);
		
		final JLabel lblBalance = new JLabel("Balance");
		lblBalance.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblBalance.setForeground(new Color(60, 169, 113));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblBalance.setForeground(new Color(204, 204, 204));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				//Removiendo todos los paneles
				mainPanel.removeAll();
				mainPanel.repaint();
				mainPanel.revalidate();
				//Añadiendo panel de balance
				mainPanel.add(balancePanel);
				mainPanel.repaint();
				mainPanel.revalidate();
			}
		});
		sl_menuPanel.putConstraint(SpringLayout.SOUTH, lblBalanceIcon, 0, SpringLayout.SOUTH, lblBalance);
		sl_menuPanel.putConstraint(SpringLayout.NORTH, lblBalance, 320, SpringLayout.NORTH, menuPanel);
		sl_menuPanel.putConstraint(SpringLayout.SOUTH, lblHistorial, -31, SpringLayout.NORTH, lblBalance);
		sl_menuPanel.putConstraint(SpringLayout.WEST, lblBalance, 0, SpringLayout.WEST, lblInicio);
		lblBalance.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 18));
		lblBalance.setForeground(new Color(204, 204, 204));
		menuPanel.add(lblBalance);
		
		final JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setText(name);
		lblUsuario.setForeground(new Color(102, 102, 102));
		lblUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblUsuario.setForeground(new Color(160, 160, 160));
				sl_background.putConstraint(SpringLayout.EAST, menuPanel, 160, SpringLayout.WEST, background);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblUsuario.setForeground(new Color(102, 102, 102));
				sl_background.putConstraint(SpringLayout.EAST, menuPanel, 150, SpringLayout.WEST, background);
			}
		});
		sl_userPanel.putConstraint(SpringLayout.NORTH, lblUsuario, 36, SpringLayout.NORTH, userPanel);
		sl_userPanel.putConstraint(SpringLayout.EAST, lblUsuario, -100, SpringLayout.EAST, userPanel);
		lblUsuario.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 16));
		userPanel.add(lblUsuario);
		
		final JLabel lblAyuda = new JLabel("Ayuda");
		lblAyuda.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblAyuda.setForeground(new Color(160, 160, 160));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblAyuda.setForeground(new Color(102, 102, 102));
			}
		});
		sl_userPanel.putConstraint(SpringLayout.NORTH, lblAyuda, 0, SpringLayout.NORTH, lblUsuario);
		sl_userPanel.putConstraint(SpringLayout.SOUTH, lblAyuda, 0, SpringLayout.SOUTH, lblUsuario);
		sl_userPanel.putConstraint(SpringLayout.EAST, lblAyuda, -30, SpringLayout.WEST, lblUsuario);
		lblAyuda.setForeground(new Color(102, 102, 102));
		lblAyuda.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 16));
		userPanel.add(lblAyuda);
		
		final JLabel lblAjustes = new JLabel("Ajustes");
		lblAjustes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblAjustes.setForeground(new Color(160, 160, 160));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblAjustes.setForeground(new Color(102, 102, 102));
			}
		});
		lblAjustes.setForeground(new Color(102, 102, 102));
		lblAjustes.setFont(new Font("Avenir LT Std 55 Roman", Font.PLAIN, 16));
		sl_userPanel.putConstraint(SpringLayout.NORTH, lblAjustes, 0, SpringLayout.NORTH, lblUsuario);
		sl_userPanel.putConstraint(SpringLayout.SOUTH, lblAjustes, 0, SpringLayout.SOUTH, lblUsuario);
		sl_userPanel.putConstraint(SpringLayout.EAST, lblAjustes, -30, SpringLayout.WEST, lblAyuda);
		userPanel.add(lblAjustes);
		
		txtBuscar = new JTextField();
		txtBuscar.addFocusListener(new FocusAdapter() {		
			@Override
			public void focusLost(FocusEvent e) {
				if(txtBuscar.getText().equals("") || txtBuscar.getText().equals("  Buscar en el inventario"))
				{
					txtBuscar.setText("  Buscar en el inventario");
					txtBuscar.setForeground(new Color(140, 140, 140));
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				if(txtBuscar.getText().equals("  Buscar en el inventario"))
				{
					txtBuscar.setText("");
					txtBuscar.setForeground(new Color(100, 100, 100));
				}
			}
		});
		txtBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!txtBuscar.getText().equals(""))
				{
					Product p = searchProduct(txtBuscar.getText());
					if(p != null)
						JOptionPane.showMessageDialog(null, "Datos del producto. \nNOMBRE:     "+p.getProduct()+"\nPRECIO:     $"+p.getPrice()+"0\nMARCA:     "+p.getBrand()+"\nCATEGORÍA:     "+p.getProductType().getType()+"\nCANTIDAD:     "+p.getQuantity(), "Producto encontrado", JOptionPane.PLAIN_MESSAGE);
					else
						JOptionPane.showMessageDialog(null, "No se encontraron coincidencias", "Producto no encontrado", JOptionPane.ERROR_MESSAGE);
					txtBuscar.setText("");
				}
			}
		});
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		sl_userPanel.putConstraint(SpringLayout.NORTH, txtBuscar, -7, SpringLayout.NORTH, lblAyuda);
		sl_userPanel.putConstraint(SpringLayout.WEST, txtBuscar, 50, SpringLayout.WEST, userPanel);
		sl_userPanel.putConstraint(SpringLayout.SOUTH, txtBuscar, 8, SpringLayout.SOUTH, lblAyuda);
		sl_userPanel.putConstraint(SpringLayout.EAST, txtBuscar, -100, SpringLayout.WEST, lblAjustes);
		txtBuscar.setText("  Buscar en el inventario");
		txtBuscar.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
		txtBuscar.setBackground(new Color(225, 225, 225));
		txtBuscar.setForeground(new Color(140, 140, 140));
		userPanel.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		final JLabel lblBuscar = new JLabel("");
		lblBuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!txtBuscar.getText().equals("")  && !txtBuscar.getText().equals("  Buscar en el inventario"))
				{
					Product p = searchProduct(txtBuscar.getText());
					if(p != null)
						JOptionPane.showMessageDialog(null, "Datos del producto. \nNOMBRE:     "+p.getProduct()+"\nPRECIO:     $"+p.getPrice()+"0\nMARCA:     "+p.getBrand()+"\nCATEGORÍA:     "+p.getProductType().getType()+"\nCANTIDAD:     "+p.getQuantity(), "Producto encontrado", JOptionPane.PLAIN_MESSAGE);
					else
						JOptionPane.showMessageDialog(null, "No se encontraron coincidencias", "Producto no encontrado", JOptionPane.ERROR_MESSAGE);
					txtBuscar.setForeground(new Color(153, 153, 153));
					txtBuscar.setText("");
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblBuscar.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/search(w).png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblBuscar.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/search.png")));
			}
		});
		sl_userPanel.putConstraint(SpringLayout.EAST, lblBuscar, -72, SpringLayout.WEST, lblAjustes);
		sl_userPanel.putConstraint(SpringLayout.NORTH, lblBuscar, 0, SpringLayout.NORTH, lblAjustes);
		lblBuscar.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/search.png")));
		userPanel.add(lblBuscar);
		
		JLabel lblUserIcon = new JLabel("");
		lblUserIcon.setIcon(new ImageIcon(Dashboard.class.getResource("/com/mexicacode/pofs/images/user.png")));
		sl_userPanel.putConstraint(SpringLayout.EAST, lblUserIcon, -50, SpringLayout.EAST, userPanel);
		sl_userPanel.putConstraint(SpringLayout.SOUTH, lblUserIcon, 10, SpringLayout.SOUTH, lblUsuario);
		sl_userPanel.putConstraint(SpringLayout.NORTH, lblUserIcon, -10, SpringLayout.NORTH, lblUsuario);
		userPanel.add(lblUserIcon);
		
	}
	
	//-----------------Métodos de impresión de datos-------------------------
	public void printWarehouse(DefaultTableModel model) {
		cleanTable(model);
		WarehouseService ws = new WarehouseService();
		List<Product> data = ws.showProducts();
		for(int i=0;i<data.size();i++)
		{
			addProductRow(model, data.get(i));
		}
	}
	
	public void printCategories(DefaultTableModel model) {
		cleanTable(model);
		WarehouseService ws = new WarehouseService();
		List<ProductType> data = ws.showProductTypes();
		for(int i=0;i<data.size();i++)													//Añadimos los registros encontrados a la base de datos
		{
			addCategoryRow(model, data.get(i));
		}
	}
	
	public void addProductRow(DefaultTableModel model, Product p) {
		Vector<String> row = new Vector<String>();
		row.add(p.getProduct());
		row.add(p.getBrand());
		row.add(p.getProductType().getType());
		row.add(String.valueOf(p.getQuantity()));
		row.add(p.getUnit());
		row.add("$"+String.format("%.2f", p.getCost()));
		row.add("$"+String.format("%.2f", p.getPrice()));
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        String strDate = dateFormat.format(p.getEntryDate());
		row.add(strDate);
		strDate = dateFormat.format(p.getDischargeDate());
		row.add(strDate);
		row.add(String.valueOf(p.getBarCode()));
		System.out.println(row);
		model.addRow(row);
	}
	
	public void addCategoryRow(DefaultTableModel model, ProductType pt) {
		Vector<String> row = new Vector<String>();
		row.add(pt.getType());
		System.out.println(row);
		model.addRow(row);
	}
	
	public void showEditProductPanel(Product p) {
		txtMarca_2.setVisible(true);
		txtMarca_2.setText(p.getBrand());
		txtCategoria_2.setVisible(true);
		txtCategoria_2.setText(p.getProductType().getType());
		txtCantidad_2.setVisible(true);
		txtCantidad_2.setText(String.valueOf(p.getQuantity()));
		txtUnidad_2.setVisible(true);
		txtUnidad_2.setText(p.getUnit());
		txtCostoCompra_2.setVisible(true);
		txtCostoCompra_2.setText(String.format("%.2f", p.getCost()));
		txtPrecioVenta_2.setVisible(true);
		txtPrecioVenta_2.setText(String.format("%.2f", p.getPrice()));
		txtMinStock_2.setVisible(true);
		txtMinStock_2.setText(String.valueOf(p.getMinStock()));
		txtMaxStock_2.setVisible(true);
		txtMaxStock_2.setText(String.valueOf(p.getMaxStock()));
		txtFechaEntrada_2.setVisible(true);
		TimeService ts = new TimeService();
		String entryDate = ts.dateToString(p.getEntryDate());
		String dischargeDate = ts.dateToString(p.getDischargeDate());
		txtFechaEntrada_2.setText(entryDate);
		txtFechaCaducidad_2.setVisible(true);
		txtFechaCaducidad_2.setText(dischargeDate);
		flag.setEnabled(true);
	}
	
	//---------------Métodos de limpieza de elementos gráficos---------------
	//Limpia los textFields del panel EditaProductosPanel
	public void cleanAddProductPanel() {
		txtNomProduct_1.setText("");
		txtCodBar_1.setText("");
		txtMarca_1.setText("");
		txtCategoria_1.setText("");
		txtCantidad_1.setText("");
		txtUnidad_1.setText("Piezas, cajas, KG,...");
		txtUnidad_1.setForeground(new Color(153, 153, 153));
		txtCostoCompra_1.setText("0.00");
		txtCostoCompra_1.setForeground(new Color(153, 153, 153));
		txtPrecioVenta_1.setText("0.00");
		txtPrecioVenta_1.setForeground(new Color(153, 153, 153));
		txtMinStock_1.setText("");
		txtMaxStock_1.setText("");
		txtFechaEntrada_1.setText("AAAA-MM-DD");
		txtFechaCaducidad_1.setText("AAAA-MM-DD");
		txtFechaCaducidad_1.setForeground(new Color(153, 153, 153));
	}
	
	public void cleanEditProductPanel() {
		txtNomProduct_2.setText("");
		txtMarca_2.setText("");
		txtMarca_2.setVisible(false);
		txtCategoria_2.setText("");
		txtCategoria_2.setVisible(false);
		txtCantidad_2.setText("");
		txtCantidad_2.setVisible(false);
		txtUnidad_2.setText("");
		txtUnidad_2.setVisible(false);
		txtCostoCompra_2.setText("");
		txtCostoCompra_2.setVisible(false);
		txtPrecioVenta_2.setText("");
		txtPrecioVenta_2.setVisible(false);
		txtMinStock_2.setText("");
		txtMinStock_2.setVisible(false);
		txtMaxStock_2.setText("");
		txtMaxStock_2.setVisible(false);
		txtFechaEntrada_2.setText("");
		txtFechaEntrada_2.setVisible(false);
		txtFechaCaducidad_2.setText("");
		txtFechaCaducidad_2.setVisible(false);
		flag.setEnabled(false);
	}
	
	//Limpia una JTable
	public void cleanTable(DefaultTableModel model) {
		while(model.getRowCount() > 0)
		{
		    model.removeRow(0);
		}
	}
	
	//------------------------Métodos de búsqueda de datos-------------------------
	public Product searchProduct(String string) { 
		Product productReturn = null;
		Product p = new Product();
		if(isBarcode(string))
			p.setBarCode(Long.parseLong(string));
		else
			p.setProduct(string.toLowerCase());
		WarehouseService ws = new WarehouseService();
		productReturn = ws.searchProduct(p);
		return productReturn;
	}
	
	public ProductType searchProductType(String string) { 
		ProductType productTypeReturn = null;
		ProductType pt = new ProductType();
		pt.setType(string);
		WarehouseService ws = new WarehouseService();
		productTypeReturn = ws.searchProductType(pt);
		return productTypeReturn;
	}
	
	public Boolean isBarcode(String string) {
		Boolean valueReturn = true;
		for(int i=0;i<string.length();i++)
		{
			if(string.charAt(i) < '0' || string.charAt(i) > '9')
			{
				valueReturn = false;
				break;
			}
		}
		return valueReturn;
	}
	
	//-----------------------Métodos de inserción de datos---------------------
	public String insertProduct() {
		//Busca la categoría en la base de datos
		String result = "";
		if(isBarcode(txtCantidad_1.getText()) && isBarcode(txtMinStock_1.getText()) && isBarcode(txtMaxStock_1.getText()) && isBarcode(txtCodBar_1.getText()))
		{	
			TimeService ts = new TimeService();
			Date fechaEntrada = ts.stringToDate(txtFechaEntrada_1.getText());
			Date fechaCaducidad = ts.stringToDate(txtFechaCaducidad_1.getText());
			Boolean aGranel;
			if(chckbxAGranel.isSelected())
				aGranel = true;
			else
				aGranel = false;
			ProductType type = new ProductType(txtCategoria_1.getText());
			Product product = new Product(	txtNomProduct_1.getText().toLowerCase(), 
											txtMarca_1.getText(), 
											Float.parseFloat(txtCostoCompra_1.getText()),
											Float.parseFloat(txtPrecioVenta_1.getText()),
											Float.parseFloat(txtCantidad_1.getText()),
											txtUnidad_1.getText(),
											Integer.parseInt(txtMinStock_1.getText()),
											Integer.parseInt(txtMaxStock_1.getText()),
											fechaEntrada,
											fechaCaducidad,
											Long.parseLong(txtCodBar_1.getText()),
											type,
											aGranel);
			System.out.println(product);
			WarehouseService ws = new WarehouseService();
			Integer exit = ws.addProduct(product);    //Añade el producto a la base de datos
			switch(exit)
			{
				case 0: result = "";
						break;
				case 1: result = "Error al guardar el producto. Asegúrate de haber llenado todos los campos correctamente";
						break;
				case 2: result = "Proporcione una categoría válida o cree una en la sección .:: CATEGORÍA ::.";
						break;
			}
		}
		else
			result = "Asegúrate de haber llenado todos los campos correctamente";
		return result;
	}
	
	public String insertProductType() {
		//Busca la categoría en la base de datos
		ProductType productType = new ProductType(txtCategory.getText());
		WarehouseService ws = new WarehouseService();
		Integer exit = ws.addProductType(productType);    //Añade el producto a la base de datos
		String result = "";
		switch(exit)
		{
			case 0: result = "";
					break;
			case 1: result = "Error al guardar la categoría";
					break;
			case 2: result = "Esta categoría ya está registrada";
					break;
		}
		return result;
	}
	
	//-----------------------Métodos de edición de información-----------------------
	public void editProduct(Product p) {
		System.out.println(p);
		TimeService ts = new TimeService();
		if(isBarcode(txtNomProduct_2.getText()))
		{
			System.out.println("Barcode");
			if(!txtNomProduct_2.getText().equals("") && !txtNomProduct_2.getText().equals(String.valueOf(p.getBarCode())))
			{
				p.setBarCode(Long.parseLong(txtNomProduct_2.getText()));
				System.out.println(p.getBarCode());
			}
		}
		else
		{
			System.out.println("Name");
			if(!txtNomProduct_2.getText().equals("") && !txtNomProduct_2.getText().equals(p.getProduct()))
			{
				p.setProduct(txtNomProduct_2.getText().toLowerCase());
				System.out.println(p.getProduct());
			}
		}
		if(!txtMarca_2.getText().equals("") && !txtMarca_2.getText().equals(p.getBrand()))
			p.setBrand(txtMarca_2.getText());
		if(!txtCantidad_2.getText().equals("") && !txtCantidad_2.getText().equals(String.valueOf(p.getQuantity())))
			p.setQuantity(Float.parseFloat(txtCantidad_2.getText()));
		if(!txtUnidad_2.getText().equals("") && !txtUnidad_2.getText().equals(p.getUnit()))
			p.setUnit(txtMarca_2.getText());
		if(!txtCostoCompra_2.getText().equals("") && !txtCostoCompra_2.getText().equals(String.format("%.2f", p.getCost())))
			p.setCost(Float.parseFloat(txtCostoCompra_2.getText()));
		if(!txtPrecioVenta_2.getText().equals("") && !txtPrecioVenta_2.getText().equals(String.format("%.2f", p.getPrice())))
			p.setPrice(Float.parseFloat(txtPrecioVenta_2.getText()));
		if(!txtMinStock_2.getText().equals("") && !txtMinStock_2.getText().equals(String.valueOf(p.getMinStock())))
			p.setMinStock(Integer.parseInt(txtMinStock_2.getText()));
		if(!txtMaxStock_2.getText().equals("") && !txtMaxStock_2.getText().equals(String.valueOf(p.getMaxStock())))
			p.setMaxStock(Integer.parseInt(txtMaxStock_2.getText()));
		if(!txtFechaEntrada_2.getText().equals("") && !txtFechaEntrada_2.getText().equals(p.getEntryDate()))
			p.setEntryDate(ts.stringToDate(txtFechaEntrada_2.getText()));
		if(!txtFechaCaducidad_2.getText().equals("") && !txtFechaCaducidad_2.getText().equals(p.getDischargeDate()))
			p.setDischargeDate(ts.stringToDate(txtFechaCaducidad_2.getText()));
		WarehouseService ws = new WarehouseService();
		System.out.println(p);
		ws.updateProduct(p);
		cleanEditProductPanel();
	}
	
	public String editProductType(ProductType productType) {
		//Busca la categoría en la base de datos
		String result = "";
		if(productType != null)
		{
			String cad = JOptionPane.showInputDialog(null, "Proporciona el nuevo \nNombre de la categoría", "Editar categoría", JOptionPane.PLAIN_MESSAGE);
			productType.setType(cad);
			WarehouseService ws = new WarehouseService();
			ws.updateProductType(productType);
		}
		else
			result = "Categoría no encontrada";
		return result;
	}
	
	//-----------------------------Métodos del servicio de ventas--------------------------------
	public void openSale() {
		if(venta == null)
		{
			ss = new SaleService();
			venta = ss.openSale();
		}
	}
	
	public void sellProducts(String string) {
		Product p = searchProduct(string);
		if(p == null)
		{
			JOptionPane.showMessageDialog(null, "El producto no se encuentra registrado en tu inventario", "Producto no encontrado", JOptionPane.INFORMATION_MESSAGE);
			txtCodigoBar.setText("");
			txtCantidad.setText("");
		}
		else
		{
			if(p.getQuantity() > 0)
			{
				if(p.getQuantity() > p.getMinStock())
				{
					DefaultTableModel model = (DefaultTableModel) tablaVentas.getModel();
					if(txtCantidad.getText().equals(""))
						cantidad = 1.0F;
					else
						cantidad = Float.parseFloat(txtCantidad.getText());
					if(cantidad > 0.0F)
					{
						Boolean band = true;
						if(p.getQuantity() - cantidad < 0)
						{
							band = false;
							Integer opcion = 2;
							do {
								String opc = JOptionPane.showInputDialog(null,"ADVERTENCIA \n\nLa cantidad de artículos a vender es mayor a la \nrestante en el inventario. \nCANTIDAD: "+p.getQuantity()+". \n¿Continuar con la venta? \n\n     1 = SÍ \n     2 = N0", "Confirmar venta",JOptionPane.WARNING_MESSAGE);
								opcion = Integer.parseInt(opc);
								if(opcion != 1 && opcion != 2)
									JOptionPane.showMessageDialog(null, "Proporciona una opción válida, por favor", "Opción inválida", JOptionPane.ERROR_MESSAGE);
							} while(opcion != 1 && opcion != 2);
							if(opcion == 1)
							{
								do {
									String cant = JOptionPane.showInputDialog(null,"¿Cuántas unidades desea vender?", "Número de artículos",JOptionPane.QUESTION_MESSAGE);
									cantidad = Float.parseFloat(cant);
									if(cantidad > p.getQuantity())
										JOptionPane.showMessageDialog(null, "Proporciona una cantidad a vender menor o igual \nque la cantidad en el inventario, por favor", "Cantidad inválida", JOptionPane.ERROR_MESSAGE);
								} while(cantidad > p.getQuantity());
								model.addRow(new String[]{	p.getProduct(), 	
															String.valueOf(p.getBarCode()), 
															"$"+String.format( "%.2f", p.getPrice()), 
															String.valueOf(cantidad)+" "+p.getUnit(), 
															"$"+String.format( "%.2f", p.getPrice()*cantidad)});
								SaleDetail sd = new SaleDetail(cantidad, p, venta);
								ss.addDetail(sd);
								txtCodigoBar.setText("");
								txtCantidad.setText("");
								subtotal += sd.getSubTotal();
								total += sd.getSubTotal();
								txtSubtotal.setText(String.format( "%.2f", subtotal));
								txtTotal.setText(String.format( "%.2f", total));
							}
							else
							{
								txtCodigoBar.setText("");
								txtCantidad.setText("");
							}
						}
						if(band)
						{
							SaleDetail sd = new SaleDetail(cantidad, p, venta);
							System.out.println(sd);
							ss.addDetail(sd);
							model.addRow(new String[]{	p.getProduct(), 
														String.valueOf(p.getBarCode()), 
														"$"+String.format( "%.2f", p.getPrice()),
														String.valueOf(sd.getQuantity())+" "+p.getUnit(), 
														"$"+String.format( "%.2f", sd.getSubTotal())});
							subtotal += sd.getSubTotal();
							total += sd.getSubTotal();
							txtSubtotal.setText(String.format( "%.2f", subtotal));
							txtTotal.setText(String.format( "%.2f", total));
						}
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Has llegado a tu STOCK MÍNIMO de "+p.getProduct().toUpperCase()+". \n\nAsegúrate de resurtirte para continuar vendiendo \n\n\nCantidad actual: "+p.getQuantity(), "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
				txtCodigoBar.setText("");
				txtCantidad.setText("");
			}
			else
				JOptionPane.showMessageDialog(null, "No quedan más unidades de "+p.getProduct().toUpperCase()+" en el inventario. \n\nAsegúrate de resurtirte para continuar vendiendo", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
		}
		
	}
}
