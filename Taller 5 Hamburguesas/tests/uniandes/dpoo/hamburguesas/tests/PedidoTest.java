package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class PedidoTest
{
    private Pedido pedido1;
    private ProductoMenu producto1;
    private ProductoMenu producto2;
    private Pedido pedido2;
    

    @BeforeEach
    void setUp( ) throws Exception
    {
    	producto1 = new ProductoMenu("corral", 14000);
    	producto2 = new ProductoMenu("especial", 24000);
    	pedido1 = new Pedido( "Julian", "AVcra68" );
    	pedido1.agregarProducto(producto1);
    	pedido1.agregarProducto(producto2);
    	pedido2 = new Pedido( "Emanuel", "AVcra50" );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    void testGetIdPedido( )
    {
        assertEquals( pedido1.getIdPedido()+1, pedido2.getIdPedido( ), "El ID del pedido no es el esperado." ); 
    }
    
    @Test
    void testGetNombreCliente( )
    {
    	assertEquals( "Julian", pedido1.getNombreCliente( ), "El nombre del cliente no es el esperado." );
        assertEquals( "Emanuel", pedido2.getNombreCliente( ), "El nombre del cliente no es el esperado." );
    }

    @Test
    void testAgregarProducto( )
    {
    	assertEquals(0, pedido2.getPrecioTotalPedido(), "El producto no se ha agregado");
    	pedido2.agregarProducto(producto2);
    	assertEquals(28560, pedido2.getPrecioTotalPedido(), "El producto no fue agregado");
    }
    
    @Test
    void testGetPrecioTotalPedido( )
    {
    	assertEquals(45220, pedido1.getPrecioTotalPedido());
    }
    
    @Test
    void testGenerarTextoFactura( )
    {
        assertEquals( ("Cliente: Julian\nDirección: AVcra68\n----------------\n" +
                producto1.generarTextoFactura( ) + producto2.generarTextoFactura( ) +
                "----------------\n" +
                "Precio Neto:  38000\nIVA:          7220\nPrecio Total: 45220\n"), 
                pedido1.generarTextoFactura( ), 
                "La factura generada no es la esperada." );
    } 
    
    @Test
    void testGuardarFactura( ) throws Exception
    {
        File archivo = new File( "data/facturaPedidoPrueba.txt" );

        pedido1.guardarFactura( archivo );

        assertTrue( archivo.exists( ), "El archivo de la factura debería existir en la carpeta data." );

        String contenidoLeido = new String( java.nio.file.Files.readAllBytes( archivo.toPath( ) ) );
        assertEquals( pedido1.generarTextoFactura( ), contenidoLeido, "El contenido de la factura guardada no es el esperado." );

        archivo.delete( );
    }
}
