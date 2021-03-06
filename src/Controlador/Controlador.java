/* @file Controlador.java
*  @brief Se encarga de comunicar la parte visual con los datos
*  			que se obtienen de la base de datos
*
*  @author Guillermo Ortega Romo
*  @date 09/11/2020
*/

package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import Ejecuta.Main;

import Modelo.GetDBData;
import Modelo.ModeloClasificacion;
import Modelo.ModeloDataset;
import Modelo.ModeloEntidad;

import Vista.Tables;
import Vista.VistaFrame;

public class Controlador implements ActionListener{
	private GetDBData modelo = Main.modelo;
	private VistaFrame vista = Main.vista;
	
	private String Xaxis = "Total";
	private int[] Options = {0, //Entidad
			0, //sexo
			0, //defuncion 
			0, //diabetes
			0, //epoc
			0, //asma
			0, //hipertensión
			0, //cardiovascular
			0, //obesidad
			0, //tabaquismo
			0, //clasificacion
			0}; //Edad
	
	//Constructor
	public Controlador() {
		this.fillComboBox();
		this.fillDataset(Options);
		
		this.vista.combo1.addActionListener(this);
		this.vista.combo2.addActionListener(this);
		this.vista.infoButton.addActionListener(this);
		
		for(JRadioButton rd : this.vista.sexR) {
			rd.addActionListener(this);
		}
		for(JRadioButton rd : this.vista.sickR) {
			rd.addActionListener(this);
		}
		for(JRadioButton rd : this.vista.deadR) {
			rd.addActionListener(this);
		}
		for(JRadioButton rd : this.vista.ageR) {
			rd.addActionListener(this);
		}
		
	}

	public void actionPerformed(ActionEvent e){
		//obtiene seleccion de ComboBox de entidad
		if(e.getSource() == this.vista.combo1){
			this.getVista().dataLabel[0] = this.getVista().combo1.getSelectedItem().toString();
			this.Options[0] = this.vista.combo1.getSelectedIndex();
			
			this.vista.Data[0].setText("Población: " + this.getVista().dataLabel[0]);
			this.vista.Data[0].repaint();
		}
		
		//Obtiene cada radio button seleccionado
		sexRButtonListeners(e);
		sickRButtonListeners(e);
		deadRButtonListeners(e);
		ageRButtonListeners(e);
		
		//obtiene seleccion de ComboBox de clasificacion
		if(e.getSource() == this.vista.combo2) {
			this.getVista().dataLabel[5] = this.getVista().combo2.getSelectedItem().toString();
			if(this.getVista().dataLabel[5].equals("TOTAL"))
				this.vista.Data[5].setText("Clasificación: " + this.getVista().dataLabel[5]);
			else
				this.vista.Data[5].setText("Clasificación: " + this.getVista().dataLabel[5].charAt(0));
			this.Options[10] = this.vista.combo2.getSelectedIndex();
			this.vista.Data[5].repaint();
		}
		
		//valida si se presiona el boton de información de clasificación
		if(e.getSource() == this.vista.infoButton) {
			this.fillInfoTable();
			JOptionPane.showMessageDialog(this.vista, this.vista.scrollPane);
		}
		
		//crea el dataset para la grafica recibiendo las opciones seleccionadas
		//por el usuario
		this.fillDataset(Options);
	}

	public void fillDataset(int[] options) {
		this.vista.dataset.clear();
		
		ArrayList<ModeloDataset> data = this.modelo.listaDataset(options);
		
		int[] row1 = new int[10];
		int[] row2 = new int[10];
		String[] ageColNames = new String[10];
		
		if(options[11] == 0) {
			if(data.isEmpty() == false) {
				row1[0] = data.get(0).getCount0();
				if(options[2] != 1 && options[2] != 2 && data.size() > 1) {
					row2[0] = data.get(1).getCount0();
				}
				if(options[2] == 3) {	
					this.vista.dataset.addValue( row1[0] , "Defunciones" , this.Xaxis );
					this.vista.dataset.addValue( row2[0] , "Contagios" , this.Xaxis );
				}else {
					this.vista.dataset.addValue( (row1[0] + row2[0]) , "Total" , this.Xaxis );
				}
			}
		}else {
			if(data.isEmpty() == false) {
				row1 = data.get(0).getCount();
				ageColNames = this.modelo.listaDataset(options).get(0).getAgeColNames();
				
				if(options[2] != 1 && options[2] != 2 && data.size() > 1) {
					row2 = data.get(1).getCount();
				}
				
				if(options[2] == 3) {	
					for(int i = 0; i < row1.length; i++) {
						this.vista.dataset.addValue( row1[i] , "Defunciones" , ageColNames[i] );
						this.vista.dataset.addValue( row2[i] , "Contagios" , ageColNames[i] );
					}
					
				}else {
					for(int i = 0; i < row1.length; i++) {
						this.vista.dataset.addValue( (row1[i] + row2[i]) , this.Xaxis , ageColNames[i] );
					}
				}
			}
		}

		this.vista.panelG.repaint();
		this.vista.panelG.updateUI();
	}
	
	//Escucha las acciones de las opciones de sexo
	public void sexRButtonListeners(ActionEvent e) {
		if(e.getSource() == this.vista.sexR[0] || e.getSource() == this.vista.sexR[1] 
		|| e.getSource() == this.vista.sexR[2]) {
			
			if(e.getSource() == this.vista.sexR[0]){
//				System.out.println("Hombre");
				this.getVista().dataLabel[1] = this.getVista().sexR[0].getText();
				this.Options[1] = 2;
			}
			if(e.getSource() == this.vista.sexR[1]){
//				System.out.println("Mujer");
				this.getVista().dataLabel[1] = this.getVista().sexR[1].getText();
				this.Options[1] = 1;
			}
			if(e.getSource() == this.vista.sexR[2]){
//				System.out.println("Total");
				this.getVista().dataLabel[1] = this.getVista().sexR[2].getText();
				this.Options[1] = 0;
			}
			this.vista.Data[1].setText("Sexo: "+ this.getVista().dataLabel[1]);
			this.vista.Data[1].repaint();	
		}
	}
	
	//Escucha las acciones de las opciones de diagnóstico
	public void sickRButtonListeners(ActionEvent e) {
		if(e.getSource() == this.vista.sickR[0] || e.getSource() == this.vista.sickR[1] 
		|| e.getSource() == this.vista.sickR[2] || e.getSource() == this.vista.sickR[3]
		|| e.getSource() == this.vista.sickR[4] || e.getSource() == this.vista.sickR[5]
		|| e.getSource() == this.vista.sickR[6] || e.getSource() == this.vista.sickR[7]) {
			
			if(e.getSource() == this.vista.sickR[0]){
	//			System.out.println("Diabetes");
				this.getVista().dataLabel[2] = this.getVista().sickR[0].getText();
				for (int i = 3; i < 10; i++) {
						this.Options[i] = 2;
				}
				this.Options[3] = 1;
				this.Xaxis = this.getVista().sickR[0].getText();
			}
			if(e.getSource() == this.vista.sickR[1]){
	//			System.out.println("EPOC");
				this.getVista().dataLabel[2] = this.getVista().sickR[1].getText();
				for (int i = 3; i < 10; i++) {
					this.Options[i] = 2;
				}
				this.Options[4] = 1;
				this.Xaxis = this.getVista().sickR[1].getText();
			}
			if(e.getSource() == this.vista.sickR[2]){
	//			System.out.println("ASMA");
				this.getVista().dataLabel[2] = this.getVista().sickR[2].getText();
				for (int i = 3; i < 10; i++) {
					this.Options[i] = 2;
				}
				this.Options[5] = 1;
				this.Xaxis = this.getVista().sickR[2].getText();
			}
			if(e.getSource() == this.vista.sickR[3]){
	//			System.out.println("Hipertensión");
				this.getVista().dataLabel[2] = this.getVista().sickR[3].getText();
				for (int i = 3; i < 10; i++) {
					this.Options[i] = 2;
				}
				this.Options[6] = 1;
				this.Xaxis = this.getVista().sickR[3].getText();
			}
			if(e.getSource() == this.vista.sickR[4]){
	//			System.out.println("Cardio");
				this.getVista().dataLabel[2] = this.getVista().sickR[4].getText();
				for (int i = 3; i < 10; i++) {
					this.Options[i] = 2;
				}
				this.Options[7] = 1;
				this.Xaxis = this.getVista().sickR[4].getText();
			}
			if(e.getSource() == this.vista.sickR[5]){
	//			System.out.println("Obesidad");
				this.getVista().dataLabel[2] = this.getVista().sickR[5].getText();
				for (int i = 3; i < 10; i++) {
					this.Options[i] = 2;
				}
				this.Options[8] = 1;
				this.Xaxis = this.getVista().sickR[5].getText();
			}
			if(e.getSource() == this.vista.sickR[6]){
	//			System.out.println("Tabaquismo");
				this.getVista().dataLabel[2] = this.getVista().sickR[6].getText();
				for (int i = 3; i < 10; i++) {
					this.Options[i] = 2;
				}
				this.Options[9] = 1;
				this.Xaxis = this.getVista().sickR[6].getText();
			}
			if(e.getSource() == this.vista.sickR[7]){
	//			System.out.println("Total");
				this.getVista().dataLabel[2] = this.getVista().sickR[7].getText();
				for (int i = 3; i < 10; i++) {
					this.Options[i] = 0;
				}
				this.Xaxis = this.getVista().sickR[7].getText();
			}
			this.vista.Data[2].setText("Diagnóstico: "+ this.getVista().dataLabel[2]);
			this.vista.Data[2].repaint();
		}
	}
	
	//Escucha las acciones de las opciones de estadística
	public void deadRButtonListeners(ActionEvent e) {
		if(e.getSource() == this.vista.deadR[0] || e.getSource() == this.vista.deadR[1] 
		|| e.getSource() == this.vista.deadR[2] || e.getSource() == this.vista.deadR[3]) {
			
			if(e.getSource() == this.vista.deadR[0]){
	//			System.out.println("Vivo");
				this.getVista().dataLabel[3] = this.getVista().deadR[0].getText();
				this.Options[2] = 1;
			}
			if(e.getSource() == this.vista.deadR[1]){
	//			System.out.println("Muerto");
				this.getVista().dataLabel[3] = this.getVista().deadR[1].getText();
				this.Options[2] = 2;
			}
			if(e.getSource() == this.vista.deadR[2]){
	//			System.out.println("Vivo vs Muerto");
				this.getVista().dataLabel[3] = this.getVista().deadR[2].getText();
				this.Options[2] = 3;
			}
			if(e.getSource() == this.vista.deadR[3]){
	//			System.out.println("Total");
				this.getVista().dataLabel[3] = this.getVista().deadR[3].getText();
				this.Options[2] = 0;
			}
			this.vista.Data[3].setText("Estadística: "+ this.getVista().dataLabel[3]);
			this.vista.Data[3].repaint();
		}
	}
	
	//Escucha las acciones de las opciones de edad
	public void ageRButtonListeners(ActionEvent e) {
		if(e.getSource() == this.vista.ageR[0] || e.getSource() == this.vista.ageR[1]) {
			
			if(e.getSource() == this.vista.ageR[0]){
	//			System.out.println("si");
				this.getVista().dataLabel[4] = this.getVista().ageR[0].getText();
				this.Options[11] = 1;
			}
			if(e.getSource() == this.vista.ageR[1]){
	//			System.out.println("no");
				this.getVista().dataLabel[4] = this.getVista().ageR[1].getText();
				this.Options[11] = 0;
			}
			this.vista.Data[4].setText("Filtro de edad: "+ this.getVista().dataLabel[4]);
			this.vista.Data[4].repaint();
		}
	}
	
	//Inserta datos en ambos comboBox
	public void fillComboBox() {
		ArrayList<ModeloEntidad> entidades = this.modelo.listaEntidades();
		ArrayList<ModeloClasificacion> clasificacion = this.modelo.listaClasificacion();
		
		for(int i=0; i < entidades.size(); i++){
			if(i==0) {
				this.vista.combo1.addItem("TOTAL");
			}
			this.vista.combo1.addItem(entidades.
					get(i).getEntidad().toString());
		}
		
		for(int i=0; i < clasificacion.size(); i++){
			if(i==0) {
				this.vista.combo2.addItem("TOTAL");
			}
			this.vista.combo2.addItem((i+1) + ".- " + modelo.listaClasificacion().get(i).getClasificacion().toString());
		}
	}
	
	//llena datos de clasificacion para la tabla de informacion
	public void fillInfoTable() {
		ArrayList<ModeloClasificacion> clasificacion = this.modelo.listaClasificacion();
		this.vista.data = new String[clasificacion.size()][clasificacion.size()];
		
		
		for(int row = 0; row < this.modelo.listaClasificacion().size(); row++) {
			this.vista.data[row][0] = String.valueOf(clasificacion.get(row).getId());
			this.vista.data[row][1] = String.valueOf(clasificacion.get(row).getClasificacion());
			this.vista.data[row][2] = String.valueOf(clasificacion.get(row).getDescripcion());
		}
//		for(int row = 0; row < this.modelo.listaClasificacion().size(); row++) {
//			for(int col = 0; col < this.vista.cNames.length; col++) {
//				System.out.println(this.vista.data[row][col]);
//			}
//		}
		this.vista.Tabla = Tables.createTable(this.vista.data, this.vista.cNames);
		this.vista.scrollPane.setViewportView(this.vista.Tabla);
	}

	public GetDBData getModelo() {
		return modelo;
	}

	public void setModelo(GetDBData modelo2) {
		this.modelo = modelo2;
	}

	public VistaFrame getVista() {
		return vista;
	}

	public void setVista(VistaFrame vista) {
		this.vista = vista;
	}
}
