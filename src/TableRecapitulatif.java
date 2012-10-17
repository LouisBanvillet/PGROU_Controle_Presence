import javax.swing.table.AbstractTableModel;


public class TableRecapitulatif extends AbstractTableModel{

	private static final long serialVersionUID = 1L;

	@Override
	public int getColumnCount() {
		return 2;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Class getColumnClass(int column) {
        switch (column) {
            case 0:
                return String.class;
            default:
                return Boolean.class;
        }
    }

	
	public String getColumnName(int column) {
	    if(column == 0){
	    	return "Nom";
	    }else{
	    	return "Excusé?";
	    }    
	}
	
	@Override
	public int getRowCount() {
		int count = 0;
		for(Etudiant e : Main.fenetreControle.getListeEtudiants())
		{
			if(!e.getPresent())
				count++;
		}
		return count;
	}

	public boolean isCellEditable(int row, int column)
	{
		return false;
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		int count = 0;
		if(col == 0){
			for(Etudiant e : Main.fenetreControle.getListeEtudiants()){
				if(!e.getPresent()){
					if(row == count)
						return e.getNom() + " " + e.getPrenom(); 
					count++;
					
				}
			}
		}else{
			for(Etudiant e : Main.fenetreControle.getListeEtudiants()){
				if(!e.getPresent()){
					if(row == count){
						return e.getExcuse();
					}
					count++;
					
				}
			}
		}
		return "Error";
	}

}
