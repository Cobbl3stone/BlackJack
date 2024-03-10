package ad.blackjack;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class ControladorRankings implements Initializable {
    @FXML
    TableView<Jugador> jugadoresRanking;

    @FXML
    TableColumn<Jugador, Integer> colDinero;

    @FXML
    TableColumn<Jugador, String> colNombre;

    @FXML
    VBox fondoRanking;

    @FXML
    Button salirRanking;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Jugador> listaRanking = FXCollections.observableArrayList();

        try {
            InputStream inputStream = getClass().getResourceAsStream("ranking.csv");
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] values = linea.split(";");
                Jugador temporal = new Jugador(values[0], Integer.valueOf(values[1]));
                listaRanking.add(temporal);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        colDinero.setCellValueFactory(new PropertyValueFactory<>("puntuacion"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        jugadoresRanking.setItems(listaRanking);
        jugadoresRanking.refresh();

        salirRanking.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ((Stage) salirRanking.getScene().getWindow()).close();
            }
        });

    }
}
