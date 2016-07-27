package org.ladbury.userInterfacePkg;

public class BooleanSelector {
	private int [] m_selected;
	private int m_nbr_selected;
	private int m_size;
	

	public BooleanSelector(int size){
		int i;
		
		if (size > 0){ m_selected = new int[size];
			for(i = 0; i < size; i++) m_selected[i] = -1;
			m_size = size;
		}
		m_nbr_selected = 0;
	}
	
	public boolean isSelected(int r){
		int i;
		
		for(i = 0; i < m_nbr_selected; i++){
			if(m_selected[i] == r) return true;
		}
		return false;
	}
	public void setSelect(int r, boolean b){
		int i;
		
		if(isSelected(r)){
			if(b) return; // nothing to do
			else for(i = 0; i<m_nbr_selected; i++){ // remove entry
					if(m_selected[i] == r){
						m_selected[m_nbr_selected-1] = -1;
						while (i< (m_nbr_selected-1)){ m_selected[i] = m_selected[i+1]; i++;} // shuffle entries down
						m_selected[m_nbr_selected-1] = -1; // clear the last entry
						m_nbr_selected--; //reduce the count 
						return;
					}
			}
		}
		else if(b & (m_nbr_selected < m_size)){ //limit selections
			m_selected[m_nbr_selected] = r;
			m_nbr_selected++;
		} // else do nothing
	}

	public int count(){
		return m_nbr_selected;
	}
}
