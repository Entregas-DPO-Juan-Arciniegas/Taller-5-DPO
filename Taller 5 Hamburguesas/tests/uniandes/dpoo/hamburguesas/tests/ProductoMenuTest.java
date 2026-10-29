package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoMenuTest
{
    private ProductoMenu productoMenu1;

    @BeforeEach
    void setUp( ) throws Exception
    {
    	productoMenu1 = new ProductoMenu( "corral", 14000 );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    void testGetNombre( )
    {
        assertEquals( "corral", productoMenu1.getNombre( ), "El nombre del productoMenu no es el esperado." );
    }

    @Test
    void testGetPrecio( )
    {
        assertEquals( 14000, productoMenu1.getPrecio( ), "El costo del productoMenu no es el esperado." );
    }
    
    @Test
    void testGenerarTextoFactura( )
    {
        assertEquals( "corral\n            14000\n", productoMenu1.generarTextoFactura( ), "La factura generada no es la esperada." );
    }

}
