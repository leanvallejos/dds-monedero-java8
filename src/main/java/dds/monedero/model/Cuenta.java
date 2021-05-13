package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private double saldo;
  private HistorialMovimientos historialMovimientos;
  private double limiteDepositosDiario = 3;
  private double cantidadMaximaExtraccion = 1000;

  public Cuenta() {
    saldo = 0;
    historialMovimientos = new HistorialMovimientos();
  }

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
    historialMovimientos = new HistorialMovimientos();
  }

  public void setHistorialMovimientos(HistorialMovimientos historialMovimientos) {
    this.historialMovimientos = historialMovimientos;
  }

  public void poner(double cuanto) {

    validarMontoIngresado(cuanto);

    validarMaximoDepositosDiario();

    agregarMovimiento(LocalDate.now(), cuanto, true);
   }

  public void sacar(double cuanto) {

    validarMontoIngresado(cuanto);

    validarExtraccionConSaldo(cuanto);

    validarExtraccionMaximaDiaria(cuanto);

    agregarMovimiento(LocalDate.now(), cuanto, false);
  }

  public void validarMontoIngresado(double cuanto){
    if (cuanto <= 0) {
      throw new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
    }
  }

  public void validarMaximoDepositosDiario(){

    if (this.historialMovimientos.cantidadDepositosDeHoy() >= this.limiteDepositosDiario) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + this.limiteDepositosDiario + " depositos diarios");
    }
  }

  public void validarExtraccionConSaldo(double cuanto){
    if (this.saldo - cuanto < 0) { //aca this.saldo()
      throw new SaldoMenorException("No puede sacar mas de " + this.saldo + " $");
    }
  }

  public void validarExtraccionMaximaDiaria(double cuanto){

    double montoExtraidoHoy = this.historialMovimientos.montoExtraidoElDia(LocalDate.now());
    double limite = this.cantidadMaximaExtraccion - montoExtraidoHoy;
    if (cuanto > limite) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + this.cantidadMaximaExtraccion
          + " diarios, límite: " + limite);
    }

  }

  public void agregarMovimiento(LocalDate fecha, double cuanto, boolean esDeposito) {
    Movimiento movimiento = new Movimiento(fecha, cuanto, esDeposito);
    historialMovimientos.agregar(movimiento);

    if(esDeposito){
      this.saldo =+ cuanto;
    }
    else {
      this.saldo =- cuanto;
    }
  }

  public double getSaldo() {
    return saldo;
  }

  public HistorialMovimientos getHistorialMovimientos(){
    return historialMovimientos;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }


}
