package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.caelum.livraria.dao.VendaDao;
import br.com.caelum.livraria.modelo.Venda;

@Named
@ViewScoped
public class VendasBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private VendaDao dao;
	
	public List<Venda> getVendas(long seed){
		return dao.listaTodos();
	}
	
	public BarChartModel getVendasModel() {
		BarChartModel model = new BarChartModel();
		
		ChartSeries series = new ChartSeries(); 
		
		series.setLabel("Vendas 2018");
		
		List<Venda> vendas = getVendas(1234);
		
		for (Venda venda : vendas) {
			series.set(venda.getLivro().getTitulo(),venda.getQuantidade());
		}
		model.addSeries(series);
		return model;
	}
}
