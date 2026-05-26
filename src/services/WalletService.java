package services;
import models.User;

/**
 * Servicio de billetera virtual.
 * Gestiona el saldo de los usuarios: consultar, recargar y descontar.
 */
public class WalletService {

    private AuthService authService;

    // ── Constructor ────────────────────────────────────────────────────────────

    public WalletService(AuthService authService) {
        // Recibe el AuthService ya creado para reutilizar la lista de usuarios
        this.authService = authService;
    }

    // ── Consultar saldo ────────────────────────────────────────────────────────

    public double consultarSaldo(User usuario) {
        return usuario.getSaldo();
    }

    // ── Recargar saldo ─────────────────────────────────────────────────────────

    /**
     * Agrega monto al saldo del usuario.
     *
     * @return mensaje de resultado
     */
    public String recargar(User usuario, double monto) {
        if (monto <= 0) {
            return "ERROR:El monto debe ser mayor a cero.";
        }
        if (monto > 500) {
            return "ERROR:El monto máximo de recarga es $500.00 por operación.";
        }

        usuario.setSaldo(usuario.getSaldo() + monto);
        authService.actualizarYGuardar();
        return "OK:Recarga exitosa. Nuevo saldo: $" + String.format("%.2f", usuario.getSaldo());
    }

    // ── Descontar saldo ────────────────────────────────────────────────────────

    /**
     * Descuenta un monto del saldo del usuario si hay suficiente saldo.
     *
     * @return true si el descuento fue exitoso, false si no hay saldo.
     */
    public boolean descontar(User usuario, double monto) {
        if (usuario.getSaldo() < monto) {
            return false;  // Saldo insuficiente
        }
        usuario.setSaldo(usuario.getSaldo() - monto);
        authService.actualizarYGuardar();
        return true;
    }
}
