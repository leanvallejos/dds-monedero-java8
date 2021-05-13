package dds.monedero.model;

import java.time.LocalDate;

public class Movimiento {
  private LocalDate fecha;
  //En ningún lenguaje de programación usen jamás doubles para modelar dinero en el mundo real
  //siempre usen numeros de precision arbitraria, como BigDecimal en Java y similares
  private double monto;
  private TipoMovimiento tipo;

  public Movimiento(LocalDate fecha, double monto, TipoMovimiento tipo) {
    this.fecha = fecha;
    this.monto = monto;
    this.tipo = tipo;
  }

  public double getMonto() {
    return monto;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public boolean esDeLaFecha(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

  public boolean isDeposito() {
    return tipo.deposito();
  } // Type tests


}
