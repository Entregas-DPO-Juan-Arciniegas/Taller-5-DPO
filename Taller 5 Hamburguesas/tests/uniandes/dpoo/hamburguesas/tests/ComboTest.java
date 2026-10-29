package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ComboTest
{
    private Combo combo1;

    @BeforeEach
    void setUp( ) throws Exception
    {
    	ArrayList<ProductoMenu> items = new ArrayList<>();
    	items.addLast(new ProductoMenu("papas medianas", 5500));
    	items.addLast(new ProductoMenu("gaseosa", 5000));
    	combo1 = new Combo("combo corral", 0.1, items);
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    void testGetNombre( )
    {
        assertEquals( "combo corral", combo1.getNombre( ), "El nombre del combo no es el esperado." );
    }

    @Test
    void testPrecio( )
    {
        assertEquals(1050, combo1.getPrecio( ), "El precio del commbo no es el esperado." );
    }
    
    @Test
    void testGenerarTextoFactura( )
    {
        assertEquals("Combo: combo corral\nDescuento: 0.1\n            1050\n", combo1.generarTextoFactura( ), "La factura generada no es la esperada." );
    }
}