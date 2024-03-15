package stratplus.examen;

import io.quarkiverse.cxf.annotation.CXFClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.tempuri.CalculatorSoap; // autoadministrado por CFX

@ApplicationScoped
public class CalculatorWsClient {

    @Inject @CXFClient("calculator-ws-client")
    CalculatorSoap calculatorService;

    public int add(int a, int b) {
        return calculatorService.add(a, b);
    }
    
    public int divide(int a, int b) {
        return calculatorService.divide(a, b);
    }
    
    public int multiply(int a, int b) {
        return calculatorService.multiply(a, b);
    }
}
