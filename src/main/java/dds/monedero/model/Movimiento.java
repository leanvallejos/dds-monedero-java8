package dds.monedero.model;

import java.time.LocalDate;

public class Movimiento {
  private LocalDate fecha;
  //En ningún lenguaje de programación usen jamás doubles para modelar dinero en el mundo real
  //siempre usen numeros de precision arbitraria, como BigDecimal en Java y similares
  private double monto;
  private boolean esDeposito; //Primitive obsession, este boolean podria ser un objeto que categorice al movimiento y tenga comportamiento polimorfico

  public Movimiento(LocalDate fecha, double monto, boolean esDeposito) {
    this.fecha = fecha;
    this.monto = monto;
    this.esDeposito = esDeposito;
  }

  public double getMonto() {
    return monto;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public boolean fueDepositado(LocalDate fecha) {
    return isDeposito() && esDeLaFecha(fecha);
  }

  public boolean fueExtraido(LocalDate fecha) {
    return isExtraccion() && esDeLaFecha(fecha);
  } // Duplicated Code que podria resolverse con polimorfismo o herencias

  public boolean esDeLaFecha(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

  public boolean isDeposito() {
    return esDeposito;
  } // Type tests

  public boolean isExtraccion() { //Type tests
    return !esDeposito;
  } //estos metodos solo aportan expresividad, porque solo hacen mucho

  public void agregateA(Cuenta cuenta) {
    cuenta.setSaldo(calcularValor(cuenta));
    cuenta.agregarMovimiento(fecha, monto, esDeposito);
  }//el movimiento no deberia mandarle un mensaje a la cuenta para que le agregue

  public double calcularValor(Cuenta cuenta) {
    if (esDeposito) { // Type tests, esto puede ser polimorfico tambien
      return cuenta.getSaldo() + getMonto();
    } else {
      return cuenta.getSaldo() - getMonto(); //hay duplicated code
    }
  }
}
