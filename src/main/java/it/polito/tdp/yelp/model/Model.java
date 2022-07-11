package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	private YelpDao dao;
	private Map<String,Business>idMap;
	private Graph<Business,DefaultWeightedEdge>grafo;
	
	public Model() {
		dao=new YelpDao();
		idMap=new HashMap<String,Business>();
	dao.getAllBusiness(idMap);
	}
	public List<String>citta(){
		return dao.citta();
	}
	public void creaGrafo(String citta) {
		this.grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(citta, idMap));
	for(Adiacenza a:this.dao.getArchi(citta, idMap)) {
		Graphs.addEdgeWithVertices(this.grafo, a.getB1(), a.getB2(),a.getPeso());
	}
	}
	public boolean grafoCreato() {
		if(this.grafo == null)
			return false;
		else 
			return true;
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	public List<Business> getVertici(){
		return new ArrayList<>(this.grafo.vertexSet());
	}
	public Adiacenza getAdiacenti(Business quartiere){
		List<Business>vicini=Graphs.neighborListOf(grafo, quartiere);
		List<Adiacenza>result=new ArrayList<>();
		Double max=Double.MIN_VALUE;
		Adiacenza distante=null;
		for(Business s:vicini) {
			
			result.add(new Adiacenza(s,this.grafo.getEdgeWeight(this.grafo.getEdge(quartiere, s))));
			
		}
		for(Adiacenza a:result) {
			if(a.getPeso()>max) {
				max=a.getPeso();
				distante=new Adiacenza(a.getB1(),a.getPeso());
			}
		}
		
		return distante;
		
	}
}
