package com.sh.app;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.sh.translate.ProcessCode;
import com.sh.translate.Translator;

public class WinTranslate extends Shell implements Observer {
	private Text text;
	private Text txtNombre;
	private Label lblNombreDelEjercicio;
	private Button btnGuardar;
	private Button btnEjecutar;
	private Button btnCompilar;

	
	private Translator trans;
	
	private ProcessCode prc;
	private Button btnCargar;
	private Group group_2;
	private Text txtTxtconsole;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			WinTranslate shell = new WinTranslate(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public WinTranslate(Display display) {
		super(display, SWT.SHELL_TRIM);
		setImage(SWTResourceManager.getImage(WinTranslate.class, "/img/011.ico"));
		setSize(new Point(1280, 832));
		setMinimumSize(new Point(100, 20));
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Group group = new Group(this, SWT.NONE);
		group.setLayout(new GridLayout(1, false));
		
		text = new Text(group, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		text.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				TxnNombreKeyReleased(evt);
			}
		});
		GridData gd_text = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_text.heightHint = 448;
		text.setLayoutData(gd_text);
		
		Group group_1 = new Group(group, SWT.NONE);
		group_1.setLayout(new RowLayout(SWT.HORIZONTAL));
		GridData gd_group_1 = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_group_1.heightHint = 44;
		gd_group_1.widthHint = 1132;
		group_1.setLayoutData(gd_group_1);
		
		lblNombreDelEjercicio = new Label(group_1, SWT.RIGHT);
		lblNombreDelEjercicio.setLayoutData(new RowData(126, 25));
		lblNombreDelEjercicio.setText("Nombre del ejercicio:");
		
		txtNombre = new Text(group_1, SWT.BORDER);
		txtNombre.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent evt) {
				TxnNombreKeyReleased(evt);
			}
			
		});
		txtNombre.setLayoutData(new RowData(264, SWT.DEFAULT));
		
		btnGuardar = new Button(group_1, SWT.NONE);
		btnGuardar.setImage(SWTResourceManager.getImage(WinTranslate.class, "/img/download bleu.png"));
		btnGuardar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent evt) {
				
				btnGuardarClick();
			}
		});
		btnGuardar.setLayoutData(new RowData(112, SWT.DEFAULT));
		btnGuardar.setText("Guardar");
		btnGuardar.setEnabled(false);
		
		
		btnCargar = new Button(group_1, SWT.NONE);
		btnCargar.setImage(SWTResourceManager.getImage(WinTranslate.class, "/img/upload bleu.png"));
		
		//btnCargar.setImage(image);
		btnCargar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent evt) {
				
				btnCargerClick();
			}
		});
		btnCargar.setLayoutData(new RowData(128, SWT.DEFAULT));
		btnCargar.setText("Cargar");
		btnCargar.setEnabled(true);
		
		btnCompilar = new Button(group_1, SWT.CENTER);
		btnCompilar.setImage(SWTResourceManager.getImage(WinTranslate.class, "/img/Setting.png"));
		btnCompilar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent evt) {
				
				btnCompilar();
			}
		});
		btnCompilar.setLayoutData(new RowData(118, SWT.DEFAULT));
		btnCompilar.setText("Compilar");
		btnCompilar.setEnabled(false);
		
		btnEjecutar = new Button(group_1, SWT.NONE);
		btnEjecutar.setImage(SWTResourceManager.getImage(WinTranslate.class, "/img/My computer Off.png"));
		btnEjecutar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent evt) {
				
				btnEjecutar();
			}
		});
		btnEjecutar.setLayoutData(new RowData(118, SWT.DEFAULT));
		btnEjecutar.setText("Ejecutar");
		btnEjecutar.setEnabled(false);
		
		group_2 = new Group(group, SWT.NONE);
		group_2.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_group_2 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_group_2.widthHint = 1249;
		group_2.setLayoutData(gd_group_2);
		
		txtTxtconsole = new Text(group_2, SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtTxtconsole.setEditable(false);
		
		trans = new Translator();
		prc = new ProcessCode();
		
		prc.addObserver(this);
		
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Programa facil");
		setSize(1280, 754);

	}
	
	//EVENTS
	
	private void TxnNombreKeyReleased(KeyEvent evt)
	{
		if(!"".equals(txtNombre.getText()) && !"".equals(text.getText()))
		{
			btnGuardar.setEnabled(true);
			btnCompilar.setEnabled(true);
			btnEjecutar.setEnabled(true);
			
		}
		else
		{
			btnGuardar.setEnabled(false);
			btnCompilar.setEnabled(false);
			btnEjecutar.setEnabled(false);
			
		}
	}
	
	private void btnGuardarClick()
	{
		trans.saveAll(txtNombre.getText(), text.getText());
	}
	
	private void btnCompilar()
	{
		getDisplay().asyncExec(new Runnable() 
		{
			
			@Override
			public void run() 
			{
				trans.saveAll(txtNombre.getText(), text.getText());
				prc.compile(txtNombre.getText());
				
			}
		});
		
	}
	private void btnEjecutar()
	{
		trans.saveAll(txtNombre.getText(), text.getText());
		prc.execute(txtNombre.getText());
	}
	
	private void btnCargerClick()
	{
		try {
			FileDialog fDlg = new FileDialog(getShell(), SWT.NONE);
			fDlg.setFilterPath(prc.getRuta());
			fDlg.setText("Elige un ejercicio.");
			String res = fDlg.open();
			if(res != null && !"".equals(res))
			{
				String txt = prc.loadFile(res);
				text.setText(txt);
				String name = new File(res).getName();
				name = name.substring(0,name.indexOf("."));
				txtNombre.setText(name);
				btnGuardar.setEnabled(true);
				btnCompilar.setEnabled(true);
				btnEjecutar.setEnabled(true);
				SimpleDateFormat sdf = new SimpleDateFormat("[dd-MM-YYYY HH:mm:ss.SSS]");
				String timestamp = sdf.format(new Date());
				txtTxtconsole.append(timestamp +" ejercicio "+ name + " cargado correctamente\n");
			}
		} 
		catch (IOException e) 
		{
		
			e.printStackTrace();
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public void update(Observable obs, Object obj) 
	{
		SimpleDateFormat sdf = new SimpleDateFormat("[dd-MM-YYYY HH:mm:ss.SSS]");
		String timestamp = sdf.format(new Date());
		txtTxtconsole.append(timestamp +" "+  (String)obj+"\n");
		
	}
}
