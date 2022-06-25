package Client.usersController;

import Common.TextFormatter;
import Common.requestSystem.responses.Response;
import Common.requestSystem.responses.SignInResponse;
import Common.requestSystem.responses.SignUpResponse;
import javafx.util.Pair;

public class UserAcceptor {

    private final AuthorizationModule authorizationModule;
    private final Response response;

    public UserAcceptor(AuthorizationModule authorizationModule, Response response) {
        this.authorizationModule = authorizationModule;
        this.response = response;
    }

    public Pair<String, String> acceptAuthorization() {
        authorizationModule.setAuthorizationDone(((SignInResponse) response).isSuccessfulAuthorization());
        if (!authorizationModule.isAuthorizationDone()) {
            TextFormatter.printErrorMessage("User not found");
        } else {
            TextFormatter.printMessage("Authorization complete");
            return ((SignInResponse) response).getPair();
        }
        return null;
    }

    public Pair<String, String> acceptRegistration() {
        authorizationModule.setAuthorizationDone(((SignUpResponse) response).isSuccessfulAuthorization());
        TextFormatter.printMessage("User successful added. Authorization complete");
        return ((SignUpResponse) response).getPair();
    }
}
