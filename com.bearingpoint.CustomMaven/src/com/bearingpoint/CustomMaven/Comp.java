package com.bearingpoint.CustomMaven;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.DisposeEvent;

public class Comp extends Composite {
	private Table table;
	private Text inputTextOtherOptions;
	private Text inputTextSkipProjects;
	private Text inputTextMvnCommand;
	private Combo comboBox;
	Button checkBtnClean;
	Button checkBtnCombo;
	Button checkBtnDevDatasets;
	Button checkBtnDev;
	Button checkBtnStopOnFail;
	Button checkBtnOneThreadPer;
	private Text textWorkspace;
	private String workspace;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public Comp(Composite parent, int style) {
		super(parent, style);
		workspace = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
		//System.out.println(workspace);
		setLayout(new GridLayout(8, false));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		checkBtnClean = new Button(this, SWT.CHECK);
		checkBtnClean.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				updateConfig(table.getSelectionIndex(), "clean", String.valueOf(btn.getSelection()));
			}
		});
		checkBtnClean.setText("clean");

		checkBtnCombo = new Button(this, SWT.CHECK);
		checkBtnCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (comboBox != null) {
					if (comboBox.getSelectionIndex() != -1) {
						Button btn = (Button) e.getSource();
						if(btn.getSelection() == true){
							if(comboBox.getSelectionIndex() == 0){
								updateConfig(table.getSelectionIndex(), "skip test execution", "true");
								updateConfig(table.getSelectionIndex(), "skip test compile", "false");
							} else if(comboBox.getSelectionIndex() == 1){
								updateConfig(table.getSelectionIndex(), "skip test compile", "true");
								updateConfig(table.getSelectionIndex(), "skip test execution", "false");
							} else {
								updateConfig(table.getSelectionIndex(), "skip test compile", "false");
								updateConfig(table.getSelectionIndex(), "skip test execution", "false");
							}
						} else {
							comboBox.setText("");
							updateConfig(table.getSelectionIndex(), "skip test compile", "false");
							updateConfig(table.getSelectionIndex(), "skip test execution", "false");
							}
					}
				}
			}

		});

		comboBox = new Combo(this, SWT.NONE);
		comboBox.setItems(new String[] { "skip test execution", "skip test compile" });
		GridData gd_comboBox = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_comboBox.widthHint = 127;
		comboBox.setLayoutData(gd_comboBox);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		comboBox.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(checkBtnCombo.getSelection()){
					if(comboBox.getSelectionIndex() == 0){
						updateConfig(table.getSelectionIndex(), "skip test execution", "true");
						updateConfig(table.getSelectionIndex(), "skip test compile", "false");
					} else if(comboBox.getSelectionIndex() == 1){
						updateConfig(table.getSelectionIndex(), "skip test compile", "true");
						updateConfig(table.getSelectionIndex(), "skip test execution", "false");
					}
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});

		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_table.heightHint = 103;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableItem tableItem = new TableItem(table, SWT.NONE);
		tableItem.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {

			}
		});
		tableItem.setText("abacus-parent");

		TableItem tableItem_1 = new TableItem(table, SWT.NONE);
		tableItem_1.setText("abacus-gwt-parent");

		TableItem tableItem_2 = new TableItem(table, SWT.NONE);
		tableItem_2.setText("davinci-parent");

		table.setSelection(0);
		

		Group grpProfile = new Group(this, SWT.NONE);
		GridData gd_grpProfile = new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1);
		gd_grpProfile.widthHint = 337;
		gd_grpProfile.heightHint = 80;
		grpProfile.setLayoutData(gd_grpProfile);
		grpProfile.setText("Profiles :");

		checkBtnDev = new Button(grpProfile, SWT.CHECK);
		checkBtnDev.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				updateConfig(table.getSelectionIndex(), "dev", String.valueOf(btn.getSelection()));
			}
		});
		checkBtnDev.setBounds(10, 25, 111, 20);
		checkBtnDev.setText("dev");

		checkBtnDevDatasets = new Button(grpProfile, SWT.CHECK);
		checkBtnDevDatasets.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				updateConfig(table.getSelectionIndex(), "devDataSets", String.valueOf(btn.getSelection()));
			}
		});
		checkBtnDevDatasets.setBounds(127, 25, 111, 20);
		checkBtnDevDatasets.setText("devDatasets");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		checkBtnStopOnFail = new Button(this, SWT.CHECK);
		checkBtnStopOnFail.setText("stop on fail");
		checkBtnStopOnFail.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				updateConfig(table.getSelectionIndex(), "stopOnFail", String.valueOf(btn.getSelection()));
			}
			
		});
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		checkBtnOneThreadPer = new Button(this, SWT.CHECK);
		checkBtnOneThreadPer.setText("one thread per core");
		checkBtnOneThreadPer.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Button btn = (Button) e.getSource();
				updateConfig(table.getSelectionIndex(), "oneThreadPerCore", String.valueOf(btn.getSelection()));
			}
			
		});
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setText("Other options: ");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		inputTextOtherOptions = new Text(this, SWT.BORDER);
		inputTextOtherOptions.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		inputTextOtherOptions.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateConfig(table.getSelectionIndex(), "otherOptions", inputTextOtherOptions.getText());
			}
		});
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setText("Skip projects: ");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		inputTextSkipProjects = new Text(this, SWT.BORDER);
		inputTextSkipProjects.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		inputTextSkipProjects.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateConfig(table.getSelectionIndex(), "skipProjects", inputTextSkipProjects.getText());
			}
		});
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Label lblMvnCommand = new Label(this, SWT.NONE);
		lblMvnCommand.setText("Mvn command:");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		inputTextMvnCommand = new Text(this, SWT.BORDER);
		inputTextMvnCommand.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		textWorkspace = new Text(this, SWT.BORDER);
		textWorkspace.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textWorkspace.setText(workspace);
		
		inputTextMvnCommand.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateConfig(table.getSelectionIndex(), "mvnCommand", inputTextMvnCommand.getText());
			}
		});
		
		/*table.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(table.getSelectionIndex() == 0){
					getConfigs(0);
				} else if (table.getSelectionIndex() == 1){
					getConfigs(1);
				} else if (table.getSelectionIndex() == 2){
					getConfigs(2);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				getConfigs(0);
			}
		});*/
		
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				if(table.getSelectionIndex() == 0){
					getConfigs(0);
				} else if (table.getSelectionIndex() == 1){
					getConfigs(1);
				} else if (table.getSelectionIndex() == 2){
					getConfigs(2);
				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				return;				
			}
		});
		
		getConfigs(0);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private void updateConfig(int parent, String key, String value) {
		OutputStream outputStream = null;
		InputStream inputStream = null;
		File configFile = null;
		String file = null;
		Properties props = new Properties();
		try {
			if (parent == 0) {
				file = workspace + "/abacus-parent" + "/abacus-parent.xml";
			} else if (parent == 1) {
				file = workspace + "/abacus-gwt-parent" + "/abacus-gwt-parent.xml";
			} else if (parent == 2) {
				file = workspace + "/davinci-parent" + "/davinci-parent.xml";
			}
			configFile = new File(file);
			inputStream = new FileInputStream(file);

					
			props.loadFromXML(inputStream);
			inputStream.close();
			
			props.setProperty(key, value);

			outputStream = new FileOutputStream(configFile);
			props.storeToXML(outputStream, "maven settings");
			outputStream.close();
		} catch (FileNotFoundException e) {
			try {
				outputStream = new FileOutputStream(file);
				outputStream.close();
				updateConfig(parent, key, value);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			props.setProperty(key, value);

			try {
				outputStream = new FileOutputStream(configFile);
				props.storeToXML(outputStream, "maven settings");
				outputStream.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}

	}
	
	private void getConfigs(int parent){
		File configFile = null;
		Properties props = new Properties();
		cleanSelections();
		if (parent == 0){
			configFile = new File(workspace + "/abacus-parent" + "/abacus-parent.xml");
		} else if (parent == 1){
			configFile = new File(workspace + "/abacus-gwt-parent" + "/abacus-gwt-parent.xml");
		} else if (parent == 2){
			configFile = new File(workspace + "/davinci-parent" + "/davinci-parent.xml");
		}
		
		try {
			InputStream reader = new FileInputStream(configFile);
			props.loadFromXML(reader);
			reader.close();
		} catch (FileNotFoundException e) {
			cleanSelections();
			return;
		} catch (InvalidPropertiesFormatException e) {
			cleanSelections();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		checkBtnClean.setSelection(Boolean.valueOf((props.getProperty("clean") == null) ? "false" : props.getProperty("clean")));
		if(props.getProperty("skip test execution") != null){
			if(props.getProperty("skip test execution").equals("true")){
				checkBtnCombo.setSelection(true);
				comboBox.select(0);
			} else {
				checkBtnCombo.setSelection(false);
				comboBox.setText("");
			}
		}
		
		if(props.getProperty("skip test compile") != null){
			if(props.getProperty("skip test compile").equals("true")){
				checkBtnCombo.setSelection(true);
				comboBox.select(1);
			} else if (props.getProperty("skip test execution").equals("false")) {
				checkBtnCombo.setSelection(false);
				comboBox.setText("");
			}
		}
		
		checkBtnDev.setSelection(Boolean.valueOf((props.getProperty("dev") == null) ? "false" : props.getProperty("dev")));
		checkBtnDevDatasets.setSelection(Boolean.valueOf((props.getProperty("devDataSets") == null) ? "false" : props.getProperty("devDataSets")));
		checkBtnOneThreadPer.setSelection(Boolean.valueOf((props.getProperty("oneThreadPerCore") == null) ? "false" : props.getProperty("oneThreadPerCore")));
		checkBtnStopOnFail.setSelection(Boolean.valueOf((props.getProperty("stopOnFail") == null) ? "false" : props.getProperty("stopOnFail")));
		
		if(props.getProperty("otherOptions") != null){
			inputTextOtherOptions.setText(props.getProperty("otherOptions"));
		} else {
			inputTextOtherOptions.setText("");
		}
		if(props.getProperty("skipProjects") != null){
			inputTextSkipProjects.setText(props.getProperty("skipProjects"));
		} else {
			inputTextSkipProjects.setText("");
		}
		if(props.getProperty("mvnCommand") != null){
			inputTextMvnCommand.setText(props.getProperty("mvnCommand"));
		} else {
			inputTextMvnCommand.setText("");
		}
			
		
	}
	
	private void cleanSelections(){
		checkBtnClean.setSelection(false);
		checkBtnCombo.setSelection(false);
		checkBtnDev.setSelection(false);
		checkBtnDevDatasets.setSelection(false);
		checkBtnOneThreadPer.setSelection(false);
		checkBtnStopOnFail.setSelection(false);
		comboBox.setText("");
		inputTextMvnCommand.clearSelection();
		inputTextOtherOptions.clearSelection();
		inputTextSkipProjects.clearSelection();
	}
}
