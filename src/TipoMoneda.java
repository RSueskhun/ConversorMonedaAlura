public enum TipoMoneda {
    USD(1, "USD", "DOLAR EEUU (USD)"),
    MXN(2, "MXN", "PESO MEXICANO (MXN)"),
    COP(3, "COP", "PESO COLOMBIANO (COP)"),
    ARS(4, "ARS", "PESO ARGENTINO (ARS)"),
    BRL(5, "BRL", "REAL BRASILEÑO (BRL)"),
    EUR(6, "EUR", "EURO (EUR)"),
    BOB(7, "BOB", "BOLIVIANO (BOB)"),
    VEF(8, "VEF", "VEF (VEF)"),
    KRC(9, "KRC", "WON (KRC)");

    private final int opcion;
    private final String codigo;
    private final String descripcion;

    TipoMoneda(int opcion, String codigo, String descripcion) {
        this.opcion = opcion;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getOpcion() {
        return opcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static TipoMoneda fromOpcion(int opcion) {
        for (TipoMoneda tipoMoneda : TipoMoneda.values()) {
            if (tipoMoneda.getOpcion() == opcion) {
                return tipoMoneda;
            }
        }
        throw new IllegalArgumentException("OPCION DE MONEDA NO DISPONIBLE EN EL MENU");
    }

    public static String menuMonedas() {
        StringBuilder menu = new StringBuilder();
        for (TipoMoneda tipoMoneda : TipoMoneda.values()) {
            menu.append(tipoMoneda.getOpcion())
                    .append("-")
                    .append(tipoMoneda.getDescripcion())
                    .append("|\n");
        }
        // Eliminar el último " | "
        if (menu.length() > 4) {
            menu.setLength(menu.length() - 4);
        }
        return menu.toString();
    }
}
