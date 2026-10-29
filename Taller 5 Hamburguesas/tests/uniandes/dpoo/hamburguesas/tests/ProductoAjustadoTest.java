package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoAjustadoTest
{
    private ProductoAjustado productoAjustado1;
    private ProductoMenu productoBase;

    @BeforeEach
    void setUp( ) throws Exception
    {
    	productoBase = new ProductoMenu( "corral", 14000 );
    	productoAjustado1 = new ProductoAjustado(productoBase);
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    void testGetNombre( )
    {
        assertEquals( "corral", productoAjustado1.getNombre( ), "El nombre del producto no es el esperado." );
    }

    @Test
    void testGePrecio( )
    {
    	Ingrediente ingAgregado = new Ingrediente( "queso", 3000 );
    	productoAjustado1.agregarIngrediente( ingAgregado );
        assertEquals( 17000, productoAjustado1.getPrecio(), "El precio del producto ajustado no es el esperado." );
    }

    @Test
    void testGenerarTextoFactura( )
    {
        Ingrediente ingAgregado = new Ingrediente( "queso", 2000 );
        Ingrediente ingEliminado = new Ingrediente( "cebolla", 0 );

        productoAjustado1.agregarIngrediente( ingAgregado );
        productoAjustado1.eliminarIngrediente( ingEliminado );

        String facturaEsperada = productoBase + "\n" +
            "    +queso     2000\n" +
            "    -cebolla\n" +
            "            16000\n";

        assertEquals( facturaEsperada, productoAjustado1.generarTextoFactura( ) );
    }
}
