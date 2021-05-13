package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MonederoTest {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta();
  }

  @Test
  void Poner() {
    cuenta.poner(1500);
  } //falta validar el test

  @Test
  void PonerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.poner(-1500));
  } //creo que deberia testear el mensaje de la exception

  @Test
  void TresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
  } //no valida nada que los movimientos tengan 3 depositos

  @Test
  void MasDeTresDepositos() { //el nombre podria estar mejor, como "LimiteDeDepositosEn
    assertThrows(MaximaCantidadDepositosException.class, () -> {
          cuenta.poner(1500);
          cuenta.poner(456);
          cuenta.poner(1900);
          cuenta.poner(245);
    }); //
  }

  @Test
  void ExtraerMasQueElSaldo() {
    assertThrows(SaldoMenorException.class, () -> {
          cuenta.setSaldo(90); //esta linea esta de mas
          cuenta.sacar(1001);
    }); //puede ser que debamos validar el mensaje de la excepcion
  }

  @Test
  public void ExtraerMasDe1000() { //este test esta de mas, no aporta
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.setSaldo(5000); //esta linea esta de mas
      cuenta.sacar(1001);
    });
  }

  @Test
  public void ExtraerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.sacar(-500));
  } //esta excepcion no corresponde, deberia haber una que sea ExtraccionNegativaException

}