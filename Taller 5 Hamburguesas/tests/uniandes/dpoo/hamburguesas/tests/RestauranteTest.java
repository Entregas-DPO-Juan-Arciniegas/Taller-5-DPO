package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.excepciones.IngredienteRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoFaltanteException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;

public class RestauranteTest
{
private Restaurante restaurante1;
	
    @BeforeEach
    public void setUp() {
        restaurante1 = new Restaurante();
        
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    void testIniciarPedido( ) throws YaHayUnPedidoEnCursoException
    {
        restaurante1.iniciarPedido( "Julian", "AVcra68" );
        assertNotNull( restaurante1.getPedidoEnCurso( ), "Debería existir un pedido en curso." );
        assertEquals( "Julian", restaurante1.getPedidoEnCurso( ).getNombreCliente( ), "El nombre del cliente no coincide." );
    }

    @Test
    void testIniciarPedidoExcepcion( ) throws YaHayUnPedidoEnCursoException
    {
        restaurante1.iniciarPedido( "Julian", "AVcra68" );
        
        assertThrows( YaHayUnPedidoEnCursoException.class, ( ) -> {
            restaurante1.iniciarPedido( "Emanuel", "AVcra50" );
        }, "Se esperaba una excepción al intentar iniciar un segundo pedido en curso." );
    }
    
    @Test
    void testCerrarYGuardarPedidoSinCursoExcepcion( )
    {
        assertThrows( NoHayPedidoEnCursoException.class, ( ) -> {
            restaurante1.cerrarYGuardarPedido( );
        }, "Se esperaba una NoHayPedidoEnCursoException al intentar cerrar un pedido inexistente." );
    }
    
    @Test
    public void testCerrarYGuardarPedidoExitoso() throws Exception {
    	File carpetaFacturas = new File("./facturas/");
        if (!carpetaFacturas.exists()) {
            carpetaFacturas.mkdirs();
        }
        
    	restaurante1.iniciarPedido("Pedro Perez", "Calle 123 #45-67");
    	int idPedido = restaurante1.getPedidoEnCurso().getIdPedido();
    	
        restaurante1.cerrarYGuardarPedido();
        assertNull(restaurante1.getPedidoEnCurso(), "No deberia haber un pedido en curso");

        
        File archivoFactura = new File("./facturas/factura_" + idPedido + ".txt");
        
        assertTrue(archivoFactura.exists(), "El archivo de la factura debería existir.");
        archivoFactura.delete();
    }
    
    
    @Test
    void testGetPedidos( ) throws Exception
    {
        restaurante1.iniciarPedido( "Carlos", "Calle 45" );
        Pedido Pedido1 = restaurante1.getPedidoEnCurso( );
        restaurante1.cerrarYGuardarPedido( );

        restaurante1.iniciarPedido( "Ana", "Calle 80" );
        Pedido Pedido2 = restaurante1.getPedidoEnCurso( );
        restaurante1.cerrarYGuardarPedido( );
        
        restaurante1.getPedidos();
        assertEquals(restaurante1.getPedidos().get(0),Pedido1,"Los pedidos cerrados no concuerdan");
        assertEquals(restaurante1.getPedidos().get(1),Pedido2,"Los pedidos cerrados no concuerdan");
        
        
        
    }
    
    
    
    
    @Test
    void testCargarInformacionRestauranteExitoso( ) throws Exception
    {
        File archivoIngredientes = new File( "data/ingredientes.txt" );
        File archivoMenu = new File( "data/menu.txt" );
        File archivoCombos = new File( "data/combos.txt" );

        restaurante1.cargarInformacionRestaurante( archivoIngredientes, archivoMenu, archivoCombos );

        assertFalse( restaurante1.getIngredientes( ).isEmpty( ), "Deberian haber ingredientes cargados en e restaurante" );
        
        assertFalse( restaurante1.getMenuBase( ).isEmpty( ) , "Deberian haber MenusBase cargados en e restaurante" );
        
        assertFalse( restaurante1.getMenuCombos( ).isEmpty( ) , "Deberian haber MenuCombos cargados en e restaurante" );
    }

    @Test
    void testCargarIngredienteRepetidoExcepcion( ) throws Exception
    {
        File archivoInvalido = File.createTempFile( "ingredientes_rep", ".txt" );
        archivoInvalido.deleteOnExit( );
        java.nio.file.Files.writeString( archivoInvalido.toPath( ), "lechuga;1000\nlechuga;1200" );

        File archivoMenu = new File( "data/menu.txt" );
        File archivoCombos = new File( "data/combos.txt" );

        assertThrows( IngredienteRepetidoException.class, ( ) -> {
            restaurante1.cargarInformacionRestaurante( archivoInvalido, archivoMenu, archivoCombos );
        } );
    }

    @Test
    void testCargarProductoRepetidoExcepcion( ) throws Exception
    {
        File archivoIngredientes = new File( "data/ingredientes.txt" );
        
        File archivoMenuInvalido = File.createTempFile( "menu_rep", ".txt" );
        archivoMenuInvalido.deleteOnExit( );
        java.nio.file.Files.writeString( archivoMenuInvalido.toPath( ), "corral;14000\ncorral;15000" );

        File archivoCombos = new File( "data/combos.txt" );

        assertThrows( ProductoRepetidoException.class, ( ) -> {
            restaurante1.cargarInformacionRestaurante( archivoIngredientes, archivoMenuInvalido, archivoCombos );
        } );
    }

    @Test
    void testCargarComboProductoFaltanteExcepcion( ) throws Exception
    {
        File archivoIngredientes = new File( "data/ingredientes.txt" );
        File archivoMenu = new File( "data/menu.txt" );

        File archivoCombosInvalido = File.createTempFile( "combos_faltante", ".txt" );
        archivoCombosInvalido.deleteOnExit( );
        java.nio.file.Files.writeString( archivoCombosInvalido.toPath( ), "combo falso;10%;productoInexistente;gaseosa" );

        assertThrows( ProductoFaltanteException.class, ( ) -> {
            restaurante1.cargarInformacionRestaurante( archivoIngredientes, archivoMenu, archivoCombosInvalido );
        } );

    }

    @Test
    void testCargarComboRepetidoExcepcion( ) throws Exception
    {
        File archivoIngredientes = new File( "data/ingredientes.txt" );
        File archivoMenu = new File( "data/menu.txt" );

        File archivoCombosInvalido = File.createTempFile( "combos_rep", ".txt" );
        archivoCombosInvalido.deleteOnExit( );
        java.nio.file.Files.writeString( archivoCombosInvalido.toPath( ), "combo especial;10%;corral;gaseosa\ncombo especial;15%;corral;gaseosa" );

        assertThrows( ProductoRepetidoException.class, ( ) -> {
            restaurante1.cargarInformacionRestaurante( archivoIngredientes, archivoMenu, archivoCombosInvalido );
        } );
    }
    
    
}