package Utilidades;

import Model.Entities.*;
import Model.Constants.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.File;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GeneradorFacturasPDF {

    private static final BaseColor COLOR_PRIMARIO = new BaseColor(13, 36, 68);     // Azul naval oscuro premium
    private static final BaseColor COLOR_SECUNDARIO = new BaseColor(44, 62, 80);   // Azul oscuro elegante
    private static final BaseColor COLOR_ACENTO = new BaseColor(0, 0, 0);          // Negro puro
    private static final BaseColor COLOR_FONDO = new BaseColor(255, 255, 255);     // Blanco puro
    private static final BaseColor COLOR_BORDE = new BaseColor(200, 200, 200);     // Gris claro bordes
    private static final BaseColor COLOR_TEXTO_OSCURO = new BaseColor(33, 37, 41); // Gris oscuro premium
    private static final BaseColor COLOR_TEXTO_MEDIO = new BaseColor(108, 117, 125); // Gris medio
    private static final BaseColor COLOR_DESTACADO = new BaseColor(13, 36, 68);    // Azul naval para destacados
    private static final BaseColor COLOR_ROJO = new BaseColor(220, 53, 69);        // Rojo para número de factura
    private static final BaseColor COLOR_VERDE = new BaseColor(40, 167, 69);       // Verde para estado PAGADA

    public static void generarFacturaPDF(Venta venta, List<ItemCarrito> items, String rutaArchivo) {
        Document doc = new Document(PageSize.A4, 50, 50, 80, 60);

        try {
            File carpeta = new File(rutaArchivo).getParentFile();
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(rutaArchivo));
            writer.setPageEvent(new HeaderFooterElegante());

            doc.open();

            generarPaginaEncabezado(doc, venta, items);

            if (!items.isEmpty()) {
                for (int i = 0; i < items.size(); i++) {
                    doc.newPage();
                    generarPaginaMoto(doc, items.get(i), i + 1, items.size());
                }

                doc.newPage();
                generarPaginaResumen(doc, venta, items);
            }

            doc.close();
            writer.close();
            System.out.println("Factura PDF generada: " + rutaArchivo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generarPaginaEncabezado(Document doc, Venta venta, List<ItemCarrito> items) throws DocumentException {
        Font tituloPrincipalFont = new Font(Font.FontFamily.HELVETICA, 28, Font.BOLD, COLOR_PRIMARIO);
        Font tituloSeccionFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, COLOR_PRIMARIO);
        Font subtituloFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, COLOR_ACENTO);
        Font textoNormalFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, COLOR_TEXTO_OSCURO);
        Font textoNegritaFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, COLOR_ACENTO);
        Font textoDestacadoFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, COLOR_DESTACADO);

        PdfPTable headerContacto = new PdfPTable(1);
        headerContacto.setWidthPercentage(100);
        headerContacto.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerContacto.setSpacingAfter(10f);

        Paragraph contacto = new Paragraph();
        contacto.add(new Chunk("📞 +57 1 234 5678", textoNormalFont));
        contacto.add(new Chunk("   |   ", textoNormalFont));
        contacto.add(new Chunk("🌐 www.voltrixmotos.com", textoNormalFont));
        contacto.add(new Chunk("   |   ", textoNormalFont));
        contacto.add(new Chunk("✉️ servicio@voltrixmotos.com", textoNormalFont));
        contacto.add(new Chunk("   |   ", textoNormalFont));
        contacto.add(new Chunk("📍 Cartagena, Colombia", textoNormalFont));
        contacto.setAlignment(Element.ALIGN_CENTER);

        PdfPCell contactoCell = new PdfPCell(contacto);
        contactoCell.setBorder(Rectangle.NO_BORDER);
        contactoCell.setPaddingBottom(5f);
        headerContacto.addCell(contactoCell);

        doc.add(headerContacto);

        doc.add(crearLineaSeparadora(COLOR_PRIMARIO, 1f));
        doc.add(Chunk.NEWLINE);

        Paragraph tituloFactura = new Paragraph("FACTURA COMERCIAL", tituloPrincipalFont);
        tituloFactura.setAlignment(Element.ALIGN_CENTER);
        tituloFactura.setSpacingAfter(20f);
        doc.add(tituloFactura);

        PdfPTable tablaInfoFactura = new PdfPTable(4);
        tablaInfoFactura.setWidthPercentage(80);
        tablaInfoFactura.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaInfoFactura.setWidths(new float[]{1.2f, 1.5f, 1, 1.5f});
        tablaInfoFactura.setSpacingAfter(30f);

        // Fila 1
        agregarCeldaInfoFactura(tablaInfoFactura, "N° FACTURA:", "FAC-" + formatearIdSeguro(venta.getIdVenta()),
                textoNegritaFont, new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, COLOR_ROJO));

        agregarCeldaInfoFactura(tablaInfoFactura, "FECHA EMISIÓN:",
                formatearFechaSegura(venta.getFechaVenta(), "dd/MM/yyyy"), textoNegritaFont, textoNormalFont);

        agregarCeldaInfoFactura(tablaInfoFactura, "ESTADO:", "PAGADA", textoNegritaFont,
                new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, COLOR_VERDE));

        agregarCeldaInfoFactura(tablaInfoFactura, "TOTAL VEHÍCULOS:", String.valueOf(items.size()), textoNegritaFont, textoNormalFont);

        doc.add(tablaInfoFactura);

        doc.add(crearLineaSeparadora(COLOR_PRIMARIO, 1f));
        doc.add(Chunk.NEWLINE);

        Paragraph tituloCliente = new Paragraph("INFORMACIÓN DEL CLIENTE", tituloSeccionFont);
        tituloCliente.setAlignment(Element.ALIGN_CENTER);
        tituloCliente.setSpacingBefore(15f);
        tituloCliente.setSpacingAfter(15f);
        doc.add(tituloCliente);

        Usuario u = venta.getUsuario();
        PdfPTable tablaCliente = new PdfPTable(2);
        tablaCliente.setWidthPercentage(70);
        tablaCliente.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaCliente.setWidths(new float[]{1, 2});
        tablaCliente.setSpacingAfter(30f);

        agregarFilaClienteElegante(tablaCliente, "NOMBRE COMPLETO:",
                u.getPrimerNombre() + " " + u.getSegundoNombre() + " " + u.getPrimerApellido(),
                textoNegritaFont, textoNormalFont);
        agregarFilaClienteElegante(tablaCliente, "IDENTIFICACIÓN:", u.getCedula(), textoNegritaFont, textoNormalFont);
        agregarFilaClienteElegante(tablaCliente, "CORREO ELECTRÓNICO:", u.getEmail(), textoNegritaFont, textoNormalFont);

        doc.add(tablaCliente);

        Paragraph tituloResumen = new Paragraph("RESUMEN EJECUTIVO", tituloSeccionFont);
        tituloResumen.setAlignment(Element.ALIGN_CENTER);
        tituloResumen.setSpacingBefore(20f);
        tituloResumen.setSpacingAfter(15f);
        doc.add(tituloResumen);

        PdfPTable tablaResumen = new PdfPTable(3);
        tablaResumen.setWidthPercentage(85);
        tablaResumen.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaResumen.setWidths(new float[]{2, 1, 1.5f});

        agregarCeldaEncabezadoElegante(tablaResumen, "CONCEPTO", COLOR_PRIMARIO);
        agregarCeldaEncabezadoElegante(tablaResumen, "CANTIDAD", COLOR_PRIMARIO);
        agregarCeldaEncabezadoElegante(tablaResumen, "VALOR TOTAL", COLOR_PRIMARIO);

        double totalVenta = 0;
        for (ItemCarrito item : items) {
            Moto moto = item.getVehiculo();
            double precio = moto.getPrecio();
            totalVenta += precio;

            agregarFilaResumenElegante(tablaResumen,
                    moto.getMarca() + " " + moto.getModelo(),
                    "1 unidad",
                    "$ " + String.format("%,.2f", precio),
                    textoNormalFont);
        }

        // Total general
        PdfPCell cellEmpty = new PdfPCell(new Phrase(""));
        cellEmpty.setBorder(Rectangle.NO_BORDER);
        cellEmpty.setPadding(8f);
        tablaResumen.addCell(cellEmpty);

        PdfPCell cellTotalLabel = new PdfPCell(new Phrase("TOTAL GENERAL:",
                new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.WHITE)));
        cellTotalLabel.setBackgroundColor(COLOR_PRIMARIO);
        cellTotalLabel.setPadding(10f);
        cellTotalLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellTotalLabel.setBorderWidth(1f);
        cellTotalLabel.setBorderColor(COLOR_BORDE);
        tablaResumen.addCell(cellTotalLabel);

        PdfPCell cellTotalValue = new PdfPCell(new Phrase("$ " + String.format("%,.2f", totalVenta),
                new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.WHITE)));
        cellTotalValue.setBackgroundColor(COLOR_PRIMARIO);
        cellTotalValue.setPadding(10f);
        cellTotalValue.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTotalValue.setBorderWidth(1f);
        cellTotalValue.setBorderColor(COLOR_BORDE);
        tablaResumen.addCell(cellTotalValue);

        doc.add(tablaResumen);
    }

    private static void generarPaginaMoto(Document doc, ItemCarrito item, int numeroMoto, int totalMotos) throws DocumentException {
        Moto moto = item.getVehiculo();

        Font tituloPaginaFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, COLOR_PRIMARIO);
        Font tituloSeccionFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, COLOR_PRIMARIO);
        Font subtituloFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, COLOR_ACENTO);
        Font textoNormalFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, COLOR_TEXTO_OSCURO);
        Font textoNegritaFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, COLOR_ACENTO);
        Font textoDestacadoFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, COLOR_DESTACADO);

        Paragraph tituloPagina = new Paragraph("VEHÍCULO #" + numeroMoto + " DE " + totalMotos, tituloPaginaFont);
        tituloPagina.setAlignment(Element.ALIGN_CENTER);
        tituloPagina.setSpacingAfter(8f);
        doc.add(tituloPagina);

        Paragraph subtituloPagina = new Paragraph(moto.getMarca().toUpperCase() + " " + moto.getModelo().toUpperCase() + " - " + moto.getPlaca(),
                new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, COLOR_SECUNDARIO));
        subtituloPagina.setAlignment(Element.ALIGN_CENTER);
        subtituloPagina.setSpacingAfter(25f);
        doc.add(subtituloPagina);

        PdfPTable tablaInfoPrincipal = new PdfPTable(2);
        tablaInfoPrincipal.setWidthPercentage(90);
        tablaInfoPrincipal.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaInfoPrincipal.setWidths(new float[]{1, 1});
        tablaInfoPrincipal.setSpacingAfter(20f);

        PdfPTable columnaIzquierda = crearPanelSeccion("INFORMACIÓN BÁSICA", COLOR_PRIMARIO);

        agregarFilaInfoDetallada(columnaIzquierda, "MARCA:", moto.getMarca(), textoNegritaFont, textoNormalFont);
        agregarFilaInfoDetallada(columnaIzquierda, "MODELO:", moto.getModelo(), textoNegritaFont, textoNormalFont);
        agregarFilaInfoDetallada(columnaIzquierda, "PLACA:", moto.getPlaca(), textoNegritaFont, textoDestacadoFont);
        agregarFilaInfoDetallada(columnaIzquierda, "AÑO:", String.valueOf(moto.getFechaIngreso()), textoNegritaFont, textoNormalFont);
        agregarFilaInfoDetallada(columnaIzquierda, "COLOR:", String.valueOf(moto.getTipoColorMoto()), textoNegritaFont, textoNormalFont);

        PdfPCell cellIzquierda = new PdfPCell(columnaIzquierda);
        estiloCeldaPanelElegante(cellIzquierda);
        tablaInfoPrincipal.addCell(cellIzquierda);

        PdfPTable columnaDerecha = crearPanelSeccion("ESPECIFICACIONES TÉCNICAS", COLOR_SECUNDARIO);

        agregarFilaInfoDetallada(columnaDerecha, "CILINDRAJE:", moto.getCilindraje() + " cc", textoNegritaFont, textoNormalFont);
        agregarFilaInfoDetallada(columnaDerecha, "TIPO MOTOCICLETA:", String.valueOf(moto.getTipoMoto()), textoNegritaFont, textoNormalFont);
        agregarFilaInfoDetallada(columnaDerecha, "ESTADO:", String.valueOf(moto.getEstado()), textoNegritaFont,
                new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, COLOR_PRIMARIO));
        agregarFilaInfoDetallada(columnaDerecha, "PRECIO:", "$ " + String.format("%,.2f", moto.getPrecio()), textoNegritaFont,
                new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, COLOR_DESTACADO));
        agregarFilaInfoDetallada(columnaDerecha, "GARANTÍA:", "12 MESES", textoNegritaFont,
                new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, COLOR_PRIMARIO));

        PdfPCell cellDerecha = new PdfPCell(columnaDerecha);
        estiloCeldaPanelElegante(cellDerecha);
        tablaInfoPrincipal.addCell(cellDerecha);

        doc.add(tablaInfoPrincipal);

        Paragraph tituloCaracteristicas = new Paragraph("CARACTERÍSTICAS Y EQUIPAMIENTO", tituloSeccionFont);
        tituloCaracteristicas.setAlignment(Element.ALIGN_CENTER);
        tituloCaracteristicas.setSpacingBefore(15f);
        tituloCaracteristicas.setSpacingAfter(12f);
        doc.add(tituloCaracteristicas);

        PdfPTable tablaCaracteristicas = new PdfPTable(2);
        tablaCaracteristicas.setWidthPercentage(90);
        tablaCaracteristicas.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaCaracteristicas.setWidths(new float[]{1, 1});
        tablaCaracteristicas.setSpacingAfter(15f);

        PdfPTable panelCaractIzq = crearPanelSeccion("EQUIPAMIENTO INCLUIDO", new BaseColor(60, 60, 60));
        agregarFilaCaracteristicaElegante(panelCaractIzq, "Sistema de Parrilla", moto.isTieneParrilla(), textoNegritaFont);
        agregarFilaCaracteristicaElegante(panelCaractIzq, "Sistema de Maletero", moto.isTieneMaletero(), textoNegritaFont);
        agregarFilaCaracteristicaElegante(panelCaractIzq, "Kit de Herramientas", true, textoNegritaFont);

        PdfPCell cellCaractIzq = new PdfPCell(panelCaractIzq);
        estiloCeldaPanelElegante(cellCaractIzq);
        tablaCaracteristicas.addCell(cellCaractIzq);

        PdfPTable panelCaractDer = crearPanelSeccion("SERVICIOS INCLUIDOS", new BaseColor(60, 60, 60));
        agregarFilaCaracteristicaElegante(panelCaractDer, "Garantía de 12 Meses", true, textoNegritaFont);
        agregarFilaCaracteristicaElegante(panelCaractDer, "Servicio Técnico", true, textoNegritaFont);
        agregarFilaCaracteristicaElegante(panelCaractDer, "Documentación Legal", true, textoNegritaFont);

        PdfPCell cellCaractDer = new PdfPCell(panelCaractDer);
        estiloCeldaPanelElegante(cellCaractDer);
        tablaCaracteristicas.addCell(cellCaractDer);

        doc.add(tablaCaracteristicas);

        PartesMoto partes = moto.getPartesMoto();
        if (partes != null) {
            Paragraph tituloTecnico = new Paragraph("DETALLES TÉCNICOS AVANZADOS", tituloSeccionFont);
            tituloTecnico.setAlignment(Element.ALIGN_CENTER);
            tituloTecnico.setSpacingBefore(20f);
            tituloTecnico.setSpacingAfter(12f);
            doc.add(tituloTecnico);

            PdfPTable tablaTecnica = new PdfPTable(2);
            tablaTecnica.setWidthPercentage(90);
            tablaTecnica.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaTecnica.setWidths(new float[]{1, 1});
            tablaTecnica.setSpacingAfter(10f);

            if (partes.getMotor() != null) {
                Motor motor = partes.getMotor();
                PdfPTable panelMotor = crearPanelSeccion("GRUPO MOTRIZ", COLOR_PRIMARIO);
                agregarFilaInfoDetallada(panelMotor, "Tipo de Motor:", motor.getTipo().name(), textoNegritaFont, textoNormalFont);
                agregarFilaInfoDetallada(panelMotor, "Cilindrada:", motor.getCilindrada() + " cc", textoNegritaFont, textoNormalFont);
                agregarFilaInfoDetallada(panelMotor, "Potencia Máxima:", motor.getPotencia() + " HP", textoNegritaFont, textoNormalFont);

                PdfPCell cellMotor = new PdfPCell(panelMotor);
                estiloCeldaPanelElegante(cellMotor);
                tablaTecnica.addCell(cellMotor);
            }

            if (partes.getChasis() != null) {
                Chasis chasis = partes.getChasis();
                PdfPTable panelChasis = crearPanelSeccion("CHASIS Y SUSPENSIÓN", COLOR_SECUNDARIO);

                // Verificar que material no sea null
                if (chasis.getMaterial() != null) {
                    agregarFilaInfoDetallada(panelChasis, "Material Chasis:", chasis.getMaterial().name(), textoNegritaFont, textoNormalFont);
                } else {
                    agregarFilaInfoDetallada(panelChasis, "Material Chasis:", "No especificado", textoNegritaFont, textoNormalFont);
                }

                // Verificar que tipo no sea null - ESTA ES LA LÍNEA QUE CAUSABA EL ERROR
                if (chasis.getTipo() != null) {
                    agregarFilaInfoDetallada(panelChasis, "Tipo de Chasis:", chasis.getTipo().toString(), textoNegritaFont, textoNormalFont);
                } else {
                    agregarFilaInfoDetallada(panelChasis, "Tipo de Chasis:", "No especificado", textoNegritaFont, textoNormalFont);
                }

                if (partes.getAsiento() != null) {
                    agregarFilaInfoDetallada(panelChasis, "Capacidad Asiento:",
                            String.valueOf(partes.getAsiento().getCapacidad()) + " personas", textoNegritaFont, textoNormalFont);
                }

                PdfPCell cellChasis = new PdfPCell(panelChasis);
                estiloCeldaPanelElegante(cellChasis);
                tablaTecnica.addCell(cellChasis);
            }

// Solo agregar la tabla si tiene al menos una celda
            if (tablaTecnica.getRows().size() > 0) {
                doc.add(tablaTecnica);
            }
        }

        Paragraph notaFinal = new Paragraph("Este vehículo ha sido inspeccionado y certificado por nuestro equipo técnico. "
                + "Cumple con todos los estándares de calidad y seguridad de Voltrix Moto.",
                new Font(Font.FontFamily.HELVETICA, 9, Font.ITALIC, COLOR_TEXTO_MEDIO));
        notaFinal.setAlignment(Element.ALIGN_CENTER);
        notaFinal.setSpacingBefore(15f);
        doc.add(notaFinal);
    }

    private static void generarPaginaResumen(Document doc, Venta venta, List<ItemCarrito> items) throws DocumentException {
        Font tituloPrincipalFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, COLOR_PRIMARIO);
        Font tituloSeccionFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, COLOR_PRIMARIO);
        Font textoNormalFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, COLOR_TEXTO_OSCURO);
        Font textoNegritaFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, COLOR_ACENTO);
        Font textoDestacadoFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, COLOR_DESTACADO);

        Paragraph tituloResumen = new Paragraph("RESUMEN FINANCIERO", tituloPrincipalFont);
        tituloResumen.setAlignment(Element.ALIGN_CENTER);
        tituloResumen.setSpacingAfter(25f);
        doc.add(tituloResumen);

        Paragraph subtituloProductos = new Paragraph("DETALLE DE PRODUCTOS ADQUIRIDOS", tituloSeccionFont);
        subtituloProductos.setAlignment(Element.ALIGN_CENTER);
        subtituloProductos.setSpacingAfter(15f);
        doc.add(subtituloProductos);

        PdfPTable tablaProductos = new PdfPTable(4);
        tablaProductos.setWidthPercentage(95);
        tablaProductos.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaProductos.setWidths(new float[]{3, 2, 2, 2});
        tablaProductos.setSpacingAfter(25f);

        // Encabezados
        agregarCeldaEncabezadoElegante(tablaProductos, "DESCRIPCIÓN DEL PRODUCTO", COLOR_PRIMARIO);
        agregarCeldaEncabezadoElegante(tablaProductos, "CANTIDAD", COLOR_PRIMARIO);
        agregarCeldaEncabezadoElegante(tablaProductos, "PRECIO UNITARIO", COLOR_PRIMARIO);
        agregarCeldaEncabezadoElegante(tablaProductos, "VALOR TOTAL", COLOR_PRIMARIO);

        double subtotal = 0;
        for (ItemCarrito item : items) {
            Moto moto = item.getVehiculo();
            double precio = moto.getPrecio();
            subtotal += precio;

            PdfPCell descCell = new PdfPCell(new Phrase(
                    moto.getMarca() + " " + moto.getModelo() + " - " + moto.getPlaca(), textoNormalFont));
            estiloCeldaProductoElegante(descCell);
            tablaProductos.addCell(descCell);

            PdfPCell cantCell = new PdfPCell(new Phrase("1", textoNormalFont));
            estiloCeldaProductoElegante(cantCell);
            cantCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaProductos.addCell(cantCell);

            PdfPCell precioCell = new PdfPCell(new Phrase("$ " + String.format("%,.2f", precio), textoNormalFont));
            estiloCeldaProductoElegante(precioCell);
            precioCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tablaProductos.addCell(precioCell);

            PdfPCell totalCell = new PdfPCell(new Phrase("$ " + String.format("%,.2f", precio), textoNormalFont));
            estiloCeldaProductoElegante(totalCell);
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tablaProductos.addCell(totalCell);
        }

        doc.add(tablaProductos);

        Paragraph tituloFinanciero = new Paragraph("DESGLOSE FINANCIERO", tituloSeccionFont);
        tituloFinanciero.setAlignment(Element.ALIGN_CENTER);
        tituloFinanciero.setSpacingAfter(15f);
        doc.add(tituloFinanciero);

        PdfPTable tablaFinanciera = new PdfPTable(2);
        tablaFinanciera.setWidthPercentage(50);
        tablaFinanciera.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaFinanciera.setWidths(new float[]{2, 2});

        double iva = subtotal * 0.19;
        double total = subtotal + iva;

        agregarFilaFinanciera(tablaFinanciera, "Subtotal:", "$ " + String.format("%,.2f", subtotal),
                textoNegritaFont, textoNormalFont);
        agregarFilaFinanciera(tablaFinanciera, "IVA (19%):", "$ " + String.format("%,.2f", iva),
                textoNegritaFont, textoNormalFont);

        PdfPCell lineaCell = new PdfPCell(new Phrase(""));
        lineaCell.setColspan(2);
        lineaCell.setBorder(Rectangle.TOP);
        lineaCell.setPadding(8f);
        lineaCell.setBackgroundColor(COLOR_BORDE);
        tablaFinanciera.addCell(lineaCell);

        agregarFilaFinanciera(tablaFinanciera, "TOTAL GENERAL:", "$ " + String.format("%,.2f", total),
                new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE),
                new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE));

        int lastRowIndex = tablaFinanciera.getRows().size() - 1;
        PdfPRow lastRow = tablaFinanciera.getRow(lastRowIndex);
        lastRow.getCells()[0].setBackgroundColor(COLOR_PRIMARIO);
        lastRow.getCells()[1].setBackgroundColor(COLOR_PRIMARIO);

        doc.add(tablaFinanciera);

        Paragraph agradecimiento = new Paragraph("¡Gracias por su confianza en Voltrix Moto!",
                new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, COLOR_PRIMARIO));
        agradecimiento.setAlignment(Element.ALIGN_CENTER);
        agradecimiento.setSpacingBefore(30f);
        agradecimiento.setSpacingAfter(15f);
        doc.add(agradecimiento);

        Paragraph infoLegal = new Paragraph(
                "Este documento constituye una factura de venta legalmente reconocida. "
                + "Conserve este documento para garantías y servicios post-venta.\n\n"
                + "Para consultas y servicio al cliente:\n"
                + "📞 +57 1 234 5678 | ✉️ servicio@voltrixmotos.com | 🌐 www.voltrixmotos.com\n\n"
                + "Voltrix Motos - Líderes en calidad y servicio desde 2010",
                new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, COLOR_TEXTO_MEDIO));
        infoLegal.setAlignment(Element.ALIGN_CENTER);
        doc.add(infoLegal);
    }

    private static void agregarCeldaInfoFactura(PdfPTable tabla, String etiqueta, String valor, Font fontEtiqueta, Font fontValor) {
        PdfPTable celdaContenedor = new PdfPTable(1);
        celdaContenedor.setWidthPercentage(100);

        // Etiqueta
        PdfPCell etiquetaCell = new PdfPCell(new Phrase(etiqueta, fontEtiqueta));
        etiquetaCell.setBorder(Rectangle.NO_BORDER);
        etiquetaCell.setPadding(2f);
        etiquetaCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdaContenedor.addCell(etiquetaCell);

        // Valor
        PdfPCell valorCell = new PdfPCell(new Phrase(valor, fontValor));
        valorCell.setBorder(Rectangle.NO_BORDER);
        valorCell.setPadding(2f);
        valorCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdaContenedor.addCell(valorCell);

        PdfPCell contenedorFinal = new PdfPCell(celdaContenedor);
        contenedorFinal.setBorder(Rectangle.NO_BORDER);
        contenedorFinal.setPadding(5f);
        tabla.addCell(contenedorFinal);
    }

    private static PdfPTable crearPanelSeccion(String titulo, BaseColor color) {
        PdfPTable tabla = new PdfPTable(1);
        tabla.setWidthPercentage(100);

        PdfPCell tituloCell = new PdfPCell(new Phrase(titulo,
                new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE)));
        tituloCell.setBackgroundColor(color);
        tituloCell.setPadding(10f);
        tituloCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tituloCell.setBorder(Rectangle.NO_BORDER);
        tabla.addCell(tituloCell);

        return tabla;
    }

    private static void agregarFilaContactoElegante(PdfPTable tabla, String texto, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPadding(2f);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tabla.addCell(cell);
    }

    private static void agregarFilaDatosElegante(PdfPTable tabla, String etiqueta, String valor, Font fontEtiqueta, Font fontValor) {
        PdfPCell etiquetaCell = new PdfPCell(new Phrase(etiqueta, fontEtiqueta));
        etiquetaCell.setBorder(Rectangle.NO_BORDER);
        etiquetaCell.setPadding(4f);
        etiquetaCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(etiquetaCell);

        PdfPCell valorCell = new PdfPCell(new Phrase(valor, fontValor));
        valorCell.setBorder(Rectangle.NO_BORDER);
        valorCell.setPadding(4f);
        valorCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tabla.addCell(valorCell);
    }

    private static void agregarFilaClienteElegante(PdfPTable tabla, String etiqueta, String valor, Font fontEtiqueta, Font fontValor) {
        PdfPCell etiquetaCell = new PdfPCell(new Phrase(etiqueta, fontEtiqueta));
        etiquetaCell.setBorderWidth(1f);
        etiquetaCell.setBorderColor(COLOR_BORDE);
        etiquetaCell.setPadding(8f);
        etiquetaCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        etiquetaCell.setBackgroundColor(new BaseColor(250, 250, 250));
        tabla.addCell(etiquetaCell);

        PdfPCell valorCell = new PdfPCell(new Phrase(valor, fontValor));
        valorCell.setBorderWidth(1f);
        valorCell.setBorderColor(COLOR_BORDE);
        valorCell.setPadding(8f);
        valorCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tabla.addCell(valorCell);
    }

    private static void agregarCeldaEncabezadoElegante(PdfPTable tabla, String texto, BaseColor color) {
        PdfPCell cell = new PdfPCell(new Phrase(texto,
                new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.WHITE)));
        cell.setBackgroundColor(color);
        cell.setPadding(10f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorderWidth(1f);
        cell.setBorderColor(COLOR_BORDE);
        tabla.addCell(cell);
    }

    private static void agregarFilaResumenElegante(PdfPTable tabla, String concepto, String cantidad, String valor, Font font) {
        PdfPCell conceptoCell = new PdfPCell(new Phrase(concepto, font));
        conceptoCell.setPadding(8f);
        conceptoCell.setBorderWidth(1f);
        conceptoCell.setBorderColor(COLOR_BORDE);
        conceptoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tabla.addCell(conceptoCell);

        PdfPCell cantidadCell = new PdfPCell(new Phrase(cantidad, font));
        cantidadCell.setPadding(8f);
        cantidadCell.setBorderWidth(1f);
        cantidadCell.setBorderColor(COLOR_BORDE);
        cantidadCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(cantidadCell);

        PdfPCell valorCell = new PdfPCell(new Phrase(valor, font));
        valorCell.setPadding(8f);
        valorCell.setBorderWidth(1f);
        valorCell.setBorderColor(COLOR_BORDE);
        valorCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(valorCell);
    }

    private static void agregarFilaInfoDetallada(PdfPTable tabla, String etiqueta, String valor, Font fontEtiqueta, Font fontValor) throws DocumentException {
        PdfPTable fila = new PdfPTable(2);
        fila.setWidthPercentage(100);
        fila.setWidths(new float[]{1, 1.2f});

        PdfPCell etiquetaCell = new PdfPCell(new Phrase(etiqueta, fontEtiqueta));
        etiquetaCell.setBorder(Rectangle.NO_BORDER);
        etiquetaCell.setPadding(6f);
        etiquetaCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        fila.addCell(etiquetaCell);

        PdfPCell valorCell = new PdfPCell(new Phrase(valor, fontValor));
        valorCell.setBorder(Rectangle.NO_BORDER);
        valorCell.setPadding(6f);
        valorCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        fila.addCell(valorCell);

        PdfPCell contenedorCell = new PdfPCell(fila);
        contenedorCell.setBorder(Rectangle.BOTTOM);
        contenedorCell.setBorderColor(COLOR_BORDE);
        contenedorCell.setBorderWidth(0.5f);
        contenedorCell.setPadding(0f);
        tabla.addCell(contenedorCell);
    }

    private static void agregarFilaCaracteristicaElegante(PdfPTable tabla, String caracteristica, boolean incluido, Font font) throws DocumentException {
        PdfPTable fila = new PdfPTable(2);
        fila.setWidthPercentage(100);
        fila.setWidths(new float[]{3, 1});

        PdfPCell descCell = new PdfPCell(new Phrase(caracteristica, font));
        descCell.setBorder(Rectangle.NO_BORDER);
        descCell.setPadding(6f);
        descCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        fila.addCell(descCell);

        String estadoTexto = incluido ? "INCLUIDO" : "NO INCLUIDO";
        BaseColor estadoColor = incluido ? COLOR_PRIMARIO : COLOR_TEXTO_MEDIO;

        PdfPCell estadoCell = new PdfPCell(new Phrase(estadoTexto,
                new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE)));
        estadoCell.setBackgroundColor(estadoColor);
        estadoCell.setBorder(Rectangle.NO_BORDER);
        estadoCell.setPadding(5f);
        estadoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        fila.addCell(estadoCell);

        PdfPCell contenedorCell = new PdfPCell(fila);
        contenedorCell.setBorder(Rectangle.BOTTOM);
        contenedorCell.setBorderColor(COLOR_BORDE);
        contenedorCell.setBorderWidth(0.5f);
        contenedorCell.setPadding(0f);
        tabla.addCell(contenedorCell);
    }

    private static void agregarFilaFinanciera(PdfPTable tabla, String concepto, String valor, Font fontConcepto, Font fontValor) {
        PdfPCell conceptoCell = new PdfPCell(new Phrase(concepto, fontConcepto));
        conceptoCell.setPadding(10f);
        conceptoCell.setBorderWidth(1f);
        conceptoCell.setBorderColor(COLOR_BORDE);
        conceptoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.addCell(conceptoCell);

        PdfPCell valorCell = new PdfPCell(new Phrase(valor, fontValor));
        valorCell.setPadding(10f);
        valorCell.setBorderWidth(1f);
        valorCell.setBorderColor(COLOR_BORDE);
        valorCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabla.addCell(valorCell);
    }

    private static void estiloCeldaPanelElegante(PdfPCell celda) {
        celda.setBorderWidth(1f);
        celda.setBorderColor(COLOR_BORDE);
        celda.setPadding(0f);
        celda.setBackgroundColor(COLOR_FONDO);
    }

    private static void estiloCeldaProductoElegante(PdfPCell celda) {
        celda.setPadding(8f);
        celda.setBorderWidth(1f);
        celda.setBorderColor(COLOR_BORDE);
        celda.setBackgroundColor(COLOR_FONDO);
    }

    private static LineSeparator crearLineaSeparadora(BaseColor color, float grosor) {
        LineSeparator line = new LineSeparator();
        line.setLineColor(color);
        line.setPercentage(100f);
        line.setLineWidth(grosor);
        return line;
    }

    private static String formatearIdSeguro(Object id) {
        if (id == null) {
            return "N/A";
        }
        try {
            int numero = Integer.parseInt(id.toString());
            return String.format("%06d", numero);
        } catch (NumberFormatException e) {
            return id.toString();
        }
    }

    private static String formatearFechaSegura(Object fecha, String formato) {
        if (fecha == null) {
            return "N/A";
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
            if (fecha instanceof java.time.LocalDateTime) {
                return ((java.time.LocalDateTime) fecha).format(formatter);
            } else if (fecha instanceof java.time.LocalDate) {
                return ((java.time.LocalDate) fecha).format(formatter);
            } else {
                return fecha.toString();
            }
        } catch (Exception e) {
            return fecha.toString();
        }
    }

    static class HeaderFooterElegante extends PdfPageEventHelper {

        private Font footerFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, new BaseColor(100, 100, 100));

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();

            // LÍNEA SUPERIOR - POSICIÓN CORREGIDA
            cb.setColorStroke(COLOR_PRIMARIO);
            cb.setLineWidth(1f);
            cb.moveTo(document.left(), document.top() + 10);
            cb.lineTo(document.right(), document.top() + 10);
            cb.stroke();

            String footerText = "Página " + writer.getPageNumber() + " • Voltrix Motos - Factura Comercial • "
                    + java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    new Phrase(footerText, footerFont),
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() - 20, 0);

            cb.setColorStroke(COLOR_BORDE);
            cb.setLineWidth(0.5f);
            cb.moveTo(document.left(), document.bottom() + 15);
            cb.lineTo(document.right(), document.bottom() + 15);
            cb.stroke();
        }
    }
}
