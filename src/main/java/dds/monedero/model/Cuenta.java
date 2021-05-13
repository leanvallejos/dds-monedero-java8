package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta { //Large class /God class, ya que la responsabilidad de la lista de movimientos se puede delegar a otro objeto

  private double saldo = 0;
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta() {
    saldo = 0;
  } //aca nose porque hace = 0 si ya tiene el atributo saldo = 0

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  } //este metodo junto con la de arriba se puede abstraer con un constructor

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public void poner(double cuanto) { //Long method

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
    if (getMovimientos().stream().filter(movimiento -> movimiento.isDeposito()).count() >= 3) { // Message chains, aca hay mucho acomplamiento, deberia pasarle el mensaje a movimientos para que si supero los 3 depositos
      throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios"); //Ese 3 podria ser una variable MaximoDepositos, ayudaria en caso de que en un futuro cambie, pero en este codigo aumentaria la complejidad
    }
  }

  public void validarExtraccionConSaldo(double cuanto){
    if (getSaldo() - cuanto < 0) { //aca this.saldo()
      throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $"); //aca tambien, y se podria abstraer en una validacion
    }
  }

  public void validarExtraccionMaximaDiaria(double cuanto){

    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    double limite = 1000 - montoExtraidoHoy;
    if (cuanto > limite) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000 //aca tambien el 1000 podria ser un atributo por si en un futuro cambie la cantidad maxima de extraccion
          + " diarios, límite: " + limite);
    }

  }

  public void agregarMovimiento(LocalDate fecha, double cuanto, boolean esDeposito) {
    Movimiento movimiento = new Movimiento(fecha, cuanto, esDeposito);
    movimientos.add(movimiento);

    if(esDeposito){
      this.saldo =+ cuanto;
    }
    else {
      this.saldo =- cuanto;
    }
  }

  public double getMontoExtraidoA(LocalDate fecha) { // Feature envy
    return getMovimientos().stream()
        .filter(movimiento -> !movimiento.isDeposito() && movimiento.getFecha().equals(fecha))
        .mapToDouble(Movimiento::getMonto) //Message chains, mucho acomplamiento, hay que darle esa responsabilidad a los movimientos o a una lista de movimiento
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

}
