package servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import interfaces.OlaMundoRMI;

// 3. A classe de DEVE implementar a interface remota
public class OlaMundoRMIImpl implements OlaMundoRMI {

    // 4. Implementação do método da interface remota
    @Override
    public String dizerOla() throws RemoteException {
        System.out.println("[SERVIDOR] Método dizerOla() foi chamado!");
        return "Olá Mundo do RMI direto do Servidor!";
    }
}