package com.esprit.examen.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.esprit.examen.entities.Produit;
import com.esprit.examen.entities.Stock;
import com.esprit.examen.repositories.ProduitRepository;
import com.esprit.examen.repositories.StockRepository;

@ExtendWith(SpringExtension.class)
public class ProduitServiceImplTest {
	
	@InjectMocks
	ProduitServiceImpl produitService;
	
    @Mock
    private ProduitRepository produitRepository;
    
    @Mock
    private StockRepository stockRepository;
    
    private Produit product1 ;
    private Produit product2 ;
    private Stock stock ;
    
    @BeforeEach
    void setUp(){
    product1 = new Produit(null,"125784","libelleProd1", 12, new Date(), new Date(),null,null,null);
    product2 = new Produit(null,"365425","libelleProd2", 12, new Date(), new Date(),null,null,null);
    stock =new Stock();
    }
	
	@Test 
	public void testAddProduit() {
    	when(produitRepository.save(product1)).thenReturn(product1);
    
    	Produit persistedProduct = produitService.addProduit(product1);
    	
		assertEquals(product1, persistedProduct); 
	} 
	
	@Test 
     void testRetrieveAllProduits() {
    	List<Produit> listOfProducts=new ArrayList<Produit>();
    	listOfProducts.add(product1);
    	listOfProducts.add(product2);
		when(produitRepository.findAll()).thenReturn(listOfProducts);
    	assertEquals(2,produitService.retrieveAllProduits().size());
    	assertEquals(listOfProducts,produitService.retrieveAllProduits());
    }
    @Test 
    public void testUpdateProduct() {
    	when(produitRepository.save(product1)).thenReturn(product1);
    	assertEquals(product1, produitService.updateProduit(product1));
    }
    @Test
     void testRetrieveProduit() {
    	when(produitRepository.findById(product1.getIdProduit())).thenReturn(Optional.of(product1));
    	
    	assertEquals(product1, produitService.retrieveProduit(product1.getIdProduit()));
    }
    @Test
    void testAssignProduitToStock() {
    	when(produitRepository.findById(product1.getIdProduit())).thenReturn(Optional.of(product1));
    	when(stockRepository.findById(stock.getIdStock())).thenReturn(Optional.of(stock));
    	when(produitRepository.save(product1)).thenReturn(product1);
    	
    	produitService.assignProduitToStock(product1.getIdProduit(), stock.getIdStock());
    	
    	verify(produitRepository,times(1)).findById(product1.getIdProduit());
    	verify(stockRepository,times(1)).findById(stock.getIdStock());
    	verify(produitRepository,times(1)).save(product1);
    	
    }
}