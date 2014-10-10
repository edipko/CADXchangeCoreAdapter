package com.spotonresponse.gui;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import com.spotonresonse.httputils.UserAuthenticationObject;
import com.spotonresponse.irwin.CADItem;
import com.spotonresponse.irwin.ChannelObject;
import com.spotonresponse.irwin.IrwinCADAdapter;
import com.spotonresponse.xchangecore.WorkProduct;

public class MainWindow extends ApplicationWindow {
	private Text xc_url;
	private Text xc_user;
	private Text xc_pass;
	private Text cad_url;
	private final FormToolkit formToolkit = new FormToolkit(
			Display.getDefault());
	private TextViewer textViewer;

	private Shell shell;
	private String status = null;

	/**
	 * Create the application window.
	 */
	public MainWindow() {
		super(null);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	/**
	 * Create contents of the application window.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		shell = container.getShell();

		container.setLayout(new FillLayout(SWT.VERTICAL));
		{
			Composite composite = new Composite(container, SWT.NONE);
			composite.setLayout(new GridLayout(2, false));
			{
				Label lblXchangecoreUrl = new Label(composite, SWT.NONE);
				lblXchangecoreUrl.setLayoutData(new GridData(SWT.RIGHT,
						SWT.CENTER, false, false, 1, 1));
				lblXchangecoreUrl.setText("Xchangecore URL");
			}
			{
				xc_url = new Text(composite, SWT.BORDER);
				xc_url.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
						false, 1, 1));
			}
			{
				Label lblNewLabel = new Label(composite, SWT.NONE);
				lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
						false, false, 1, 1));
				lblNewLabel.setText("Xchangecore Username");
			}
			{
				xc_user = new Text(composite, SWT.BORDER);
				xc_user.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
						false, 1, 1));
			}
			{
				Label lblNewLabel_1 = new Label(composite, SWT.NONE);
				lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
						false, false, 1, 1));
				lblNewLabel_1.setText("Xchangecore Password");
			}
			{
				xc_pass = new Text(composite, SWT.BORDER);
				xc_pass.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
						false, 1, 1));
			}
			{
				Label lblNewLabel_2 = new Label(composite, SWT.NONE);
				lblNewLabel_2.setText("");
			}
			new Label(composite, SWT.NONE);
			{
				Label lblCadFileUrl = new Label(composite, SWT.NONE);
				lblCadFileUrl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER,
						false, false, 1, 1));
				lblCadFileUrl.setText("CAD File URL");
			}
			{
				cad_url = new Text(composite, SWT.BORDER);
				cad_url.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
						false, 1, 1));
			}
			Button btnPushFile = formToolkit.createButton(composite,
					"Push File", SWT.NONE);
			btnPushFile.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseUp(MouseEvent e) {
					fetchData();
				}
			});
			GridData gd_btnPushFile = new GridData(GridData.FILL_BOTH);
			gd_btnPushFile.verticalAlignment = SWT.CENTER;
			gd_btnPushFile.horizontalAlignment = SWT.CENTER;
			btnPushFile.setLayoutData(gd_btnPushFile);
			new Label(composite, SWT.NONE);
		}
		{
			Composite composite = new Composite(container, SWT.NONE);

			textViewer = new TextViewer(composite, SWT.BORDER | SWT.MULTI
					| SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);

			StyledText styledText = textViewer.getTextWidget();
			styledText.setEditable(false);
			styledText.setBounds(10, 87, 430, 91);
		}

		xc_url.setText("https://test3.xchangecore.leidos.com");
		xc_user.setText("spoton");
		xc_pass.setText("spoton2013");
		cad_url.setText("ftp://gis.sc.gov/scgisdata/forestry/public_wild_out.xml");
		
		return container;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the menu manager.
	 * 
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");
		return menuManager;
	}

	/**
	 * Create the toolbar manager.
	 * 
	 * @return the toolbar manager
	 */
	@Override
	protected ToolBarManager createToolBarManager(int style) {
		ToolBarManager toolBarManager = new ToolBarManager(style);
		return toolBarManager;
	}

	/**
	 * Create the status line manager.
	 * 
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			MainWindow window = new MainWindow();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * 
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("CAD/Xchangecore Tool");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 445);
	}

	private void fetchData() {
		textViewer.setEditable(false);
		status = "Fetching file: " + cad_url.getText();
		Document document = new Document(status);
		textViewer.setDocument(document);

		URL site = null;
		try {
			ChannelObject channels = null;
			site = new URL(cad_url.getText());
			String host = site.getHost();
			String protocol = site.getProtocol();
			String file = site.getFile();

			//if (protocol.toLowerCase().equals("ftp")) {
				channels = IrwinCADAdapter.getFtpData(cad_url.getText(), textViewer);
		//	}

			// Get the XChangeCore data
			UserAuthenticationObject ua = new UserAuthenticationObject(
					xc_user.getText(), xc_pass.getText());
			// If there are incidents in the RSS feed - proceed.
			if (channels.getItems() != null) {
				// Get the XChangeCore Data
				List<WorkProduct> wplist = IrwinCADAdapter
						.getXchangeCoreData(
								xc_url.getText()
										+ "/uicds/core/ws/services/IncidentManagementService",
								ua);

				// Loop through the FTP Data
				DecimalFormat df = new DecimalFormat("00.00000");

				for (CADItem item : channels.getItems()) {
					// Determine if we already have this in the XChangeCore
					String item_lat = df.format(item.getLatitude());
					String item_lng = df.format(item.getLongitude());

					String cadstring = item_lat + "|" + item_lng;
					// Loop through the incidents and determine if we already
					// have a
					// matching
					// latitude/longitude
					boolean found = false;
					for (WorkProduct wp : wplist) {
						String[] pos = wp.getPosition().replace(" ", "")
								.split(",");
						String lat = df.format(Double.parseDouble(pos[0]));
						String lon = df.format(Double.parseDouble(pos[1]));
						String xcorestring = lat + "|" + lon;
						if (cadstring.equals(xcorestring)) {
							found = true;
						}
					}

					// If not found - add it to the XchangeCore
					if (!found) {
						IrwinCADAdapter.populateXchangeCore(item,
								xc_user.getText(), xc_user.getText(),
								xc_pass.getText());
					} else {
						System.out.println("Already have");
					}
				}

			} else {
				status += "\nNo FTP Data";
				document = new Document(status);
				textViewer.setDocument(document);
			}
		} catch (MalformedURLException e1) {
			MessageBox dialog = new MessageBox(shell, SWT.ICON_QUESTION
					| SWT.OK);
			dialog.setText("Error!");
			dialog.setMessage("Must specify a valid URL");
			dialog.open();
		}
	}

}
