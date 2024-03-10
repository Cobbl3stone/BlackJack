module ad.blackjack {
    requires javafx.controls;
    requires javafx.fxml;
    requires usuario.demo;
    requires javafx.graphics;

    opens ad.blackjack to javafx.fxml;
    exports ad.blackjack;
}