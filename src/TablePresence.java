import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.table.AbstractTableModel;


public class TablePresence extends AbstractTableModel{

	private boolean prepare = false;
	
	public TablePresence(){
		super();		
	}
	
	public String getColumnName(int column) {
	    if(column == 0){
	    	return "Nom";	
	    }else if(column == 1){
	    	return "Présent";
	    }else{
	    	return "Excusé";
	    }
	    
	}
	
	
	public boolean isCellEditable(int row, int column)
	{
		switch (column) {
			case 0:
				return false;
			case 1:
				return true;
			default:
				return true;		
		}
	}
	
	public void setValueAt(Object aVal, int row, int col){
		if(col == 1){
			ListeEtudiants.etudiants.get(row).setPresent((Boolean) aVal);
		}else if(col == 2){
			if(!ListeEtudiants.etudiants.get(row).getPresent())
				ListeEtudiants.etudiants.get(row).setExcuse((Boolean) aVal);
		}
		
	}
	
    public Class getColumnClass(int column) {
        switch (column) {
            case 0:
                return String.class;
            case 1:
                return Boolean.class;
            case 2:
                return Boolean.class;
            default:
                return Boolean.class;
        }
    }

	
	
	@Override
	public int getColumnCount() {
		//c'est constant
		return 3;
	}

	@Override
	public int getRowCount() {
		return ListeEtudiants.etudiants.size();
	}
	
	
	
	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub

		if(arg1 == 0){
			return (Object) ListeEtudiants.etudiants.get(arg0).getNom() + " " + ListeEtudiants.etudiants.get(arg0).getPrenom(); 
		}else if(arg1 == 1){
			return (Object) ListeEtudiants.etudiants.get(arg0).getPresent();
		}else{
			return (Object) ListeEtudiants.etudiants.get(arg0).getExcuse();
		}
	}

}
