package ad.blackjack;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import usuario.demo.Usuario;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BlackJackControlador implements Initializable {

    //Elemtos Visuales

    @FXML
    VBox fondo;

    @FXML
    Label tuPuntuacion;

    @FXML
    Label npcPuntuacion;

    @FXML
    AnchorPane ordenadorPov;

    @FXML
    AnchorPane jugadorPov;

    @FXML
    VBox pararBox;

    @FXML
    VBox pedirBox;

    @FXML
    Button btnPlay = new Button();

    @FXML
    VBox opciones;

    @FXML
    Button btnRanking = new Button();

    @FXML
    Button btnCerrar = new Button();

    @FXML
    Button btnParar;

    @FXML
    Button btnPedir;


    @FXML
    Button salir = new Button();

    @FXML
    HBox jugadorLado = new HBox();

    @FXML
    HBox ordenadorLado = new HBox();

    @FXML
    Label saldo;

    //Instancia de jugador para el manejo de datos de partidas

    Jugador player;
    ObservableList<Jugador> listaJugadoresRanking;

    //Elementos visuales de botones

    BorderStroke bordeIn = new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3));
    BorderStroke bordeOut = new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3));
    CornerRadii radios = new CornerRadii(10);
    BackgroundFill fondoJuego = new BackgroundFill(Color.BLACK, radios, null);

    //Listas de barajas

    List<Carta> baraja;
    List<Carta> ordenador;
    List<Carta> jugador;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        player = new Jugador();
        mostrarOpciones();
    }

    //Método para iniciar la partida
    public void iniciarPartida() {

        //Se limpia todos los cambios de la partida anterior

        mostrarOpciones();

        opciones.setBackground(null);
        opciones.getChildren().remove(0, 2);

        //Se oculta la selección de opciones

        btnPlay.setVisible(false);
        btnRanking.setVisible(false);
        btnCerrar.setVisible(false);

        //Se crean los mazos y las barajas

        this.baraja = new ArrayList<>();
        this.ordenador = new ArrayList<>();
        this.jugador = new ArrayList<>();
        this.crearBaraja();

        //Se muestra oda la información y botones de juego

        jugadorLado.setVisible(true);
        ordenadorLado.setVisible(true);
        pararBox.setVisible(true);
        pedirBox.setVisible(true);
        tuPuntuacion.setVisible(true);
        npcPuntuacion.setVisible(true);
        saldo.setVisible(true);

        //Se entregan las dos primeras cartas a los jugadores

        cartaOrdenador();
        cartaOrdenador();
        cartaJugador();
        cartaJugador();

        saldo.setText("Saldo: " + player.getPuntuacion() + "€");

    }

    //Deshace todos los cambios de la partida para poder empezar una nueva
    public void mostrarOpciones() {

        //Limpia el tablero

        limpiarTablero();

        //Se crean los botones de las opciones y se muestran

        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Misceláneo/logo.png")));
        ImageView iconoLogo = new ImageView();
        Image imagePlay = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Misceláneo/play.png")));
        ImageView iconoPlay = new ImageView();
        Image imageRanking = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Misceláneo/ranking.png")));
        ImageView iconoRanking = new ImageView();
        Image imageCerrar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Misceláneo/cerrar.png")));
        ImageView iconoCerrar = new ImageView();

        iconoLogo.setImage(logo);
        iconoLogo.setPreserveRatio(true);
        iconoLogo.setFitHeight(120);

        iconoPlay.setImage(imagePlay);
        iconoPlay.setPreserveRatio(true);
        iconoPlay.setFitHeight(40);

        iconoRanking.setImage(imageRanking);
        iconoRanking.setPreserveRatio(true);
        iconoRanking.setFitHeight(40);

        iconoCerrar.setImage(imageCerrar);
        iconoCerrar.setPreserveRatio(true);
        iconoCerrar.setFitHeight(40);

        opciones.getChildren().addAll(iconoLogo, btnPlay, btnRanking, btnCerrar);

        btnCerrar.setGraphic(iconoCerrar);
        btnCerrar.setText("Cerrar App");
        btnCerrar.setTextFill(Color.WHITE);
        btnCerrar.setFont(Font.font("Frank Ruehl CLM", 30));
        btnCerrar.setBackground(null);
        btnCerrar.setContentDisplay(ContentDisplay.RIGHT);
        btnCerrar.setVisible(true);
        btnCerrar.setBorder(new Border(bordeOut));
        btnCerrar.setOnMouseClicked(mouseEvent -> {
            Platform.exit();
        });
        btnCerrar.setOnMouseEntered(mouseEvent ->
                btnCerrar.setBorder(new Border(bordeIn))
        );
        btnCerrar.setOnMouseExited(mouseEvent ->
                btnCerrar.setBorder(new Border(bordeOut))
        );

        btnPlay.setGraphic(iconoPlay);
        btnPlay.setText("Jugar");
        btnPlay.setFont(Font.font("Frank Ruehl CLM", 30));
        btnPlay.setTextFill(Color.WHITE);
        btnPlay.setBackground(null);
        btnPlay.setContentDisplay(ContentDisplay.RIGHT);
        btnPlay.setBorder(new Border(bordeOut));
        btnPlay.setOnMouseEntered(mouseEvent ->
                btnPlay.setBorder(new Border(bordeIn))
        );
        btnPlay.setOnMouseExited(mouseEvent ->
                btnPlay.setBorder(new Border(bordeOut))
        );

        btnPlay.setOnMouseClicked(event -> iniciarPartida());

        opciones.setSpacing(20);
        btnRanking.setGraphic(iconoRanking);
        btnRanking.setText("Ranking");
        btnRanking.setTextFill(Color.WHITE);
        btnRanking.setFont(Font.font("Frank Ruehl CLM", 30));
        btnRanking.setBackground(null);
        btnRanking.setContentDisplay(ContentDisplay.RIGHT);
        btnRanking.setVisible(true);
        btnRanking.setBorder(new Border(bordeOut));
        btnRanking.setOnMouseClicked(mouseEvent -> {
            mostrarRanking();
        });
        btnRanking.setOnMouseEntered(mouseEvent ->
                btnRanking.setBorder(new Border(bordeIn))
        );
        btnRanking.setOnMouseExited(mouseEvent ->
                btnRanking.setBorder(new Border(bordeOut))
        );

        //Se oculta toda la información y botones de juego

        jugadorLado.setVisible(false);
        saldo.setVisible(false);
        ordenadorLado.setVisible(false);
        pararBox.setVisible(false);
        pedirBox.setVisible(false);
        tuPuntuacion.setVisible(false);
        npcPuntuacion.setVisible(false);

        //Se reinician puntuaciones

        tuPuntuacion.setText("Tu puntuación: 0");
        npcPuntuacion.setText("Puntuación npc: ??");

    }

    private void limpiarTablero() {

        //Se limpian los mazos de ambos jugadores

        ordenadorPov.getChildren().clear();
        jugadorPov.getChildren().clear();
        opciones.getChildren().clear();

        //Se habilitan los botones de juego

        btnPedir.setDisable(false);
        btnPedir.setOnMouseEntered(mouseEvent ->
                btnPedir.setBorder(new Border(bordeIn))
        );
        btnPedir.setOnMouseExited(mouseEvent ->
                btnPedir.setBorder(new Border(bordeOut))
        );
        btnParar.setDisable(false);
        btnParar.setOnMouseEntered(mouseEvent ->
                btnParar.setBorder(new Border(bordeIn))
        );
        btnParar.setOnMouseExited(mouseEvent ->
                btnParar.setBorder(new Border(bordeOut))
        );

    }


    //Método para continuar cuando termina el turno del jugador
    public void terminarTurno() {

        //Instanciamos el texto que dirá cual es el desenlace de la partida

        Label desenlace = new Label();
        desenlace.setBackground(null);
        desenlace.setFont(Font.font("Frank Ruehl CLM", 80));

        //Se voltea la primera carta de la máquina para comparar puntuaciones

        mostrarCartaOrdenador();
        npcPuntuacion.setText("Puntuación npc: " + puntuarOrdenador());

        //Comprobamos si la puntuación del jugador supera los 21 puntos

        if (puntuarJugador() < 22) {

            //La puntuacion del jugador no supera los 21 puntos

            //Comprobamos si hay algún BlackJack

            if (puntuarJugador() == 21 || (puntuarOrdenador() == 21)) {

                //Hay por lo menos un BlackJack

                if (puntuarOrdenador() == puntuarJugador()) {

                    //Ambos tienen BlackJack (Empate)

                    System.out.println("EMPATE");
                    desenlace.setText("EMPATE");
                    desenlace.setTextFill(Color.ORANGE);

                    player.setPuntuacion(player.getPuntuacion() - 100);
                    saldo.setText("Saldo: " + player.getPuntuacion() + "€");

                } else {

                    //Solo hay un BlackJack

                    if (puntuarJugador() == 21) {

                        //El jugador tiene BlackJack (Victoria)

                        System.out.println("VICTORIA");

                        desenlace.setText("VICTORIA");
                        desenlace.setTextFill(Color.LIGHTGREEN);

                        player.setPuntuacion(player.getPuntuacion() + 200);
                        saldo.setText("Saldo: " + player.getPuntuacion() + "€");


                    } else {

                        //La maquina tiene BlackJack (Derrota)

                        System.out.println("DERROTA");
                        desenlace.setText("DERROTA");
                        desenlace.setTextFill(Color.RED);

                        player.setPuntuacion(player.getPuntuacion() - 100);
                        saldo.setText("Saldo: " + player.getPuntuacion() + "€");

                    }
                }

            } else {

                //No hay ningún BlackJack

                //Turno de la máquina

                //Si la máquina tiene menos puntos que el jugador pedirá una carta

                while (puntuarOrdenador() < puntuarJugador()) {
                    cartaOrdenador();
                }

                //La máquina ya tiene por lo menos 17 puntos y supera la puntuación del jugador

                npcPuntuacion.setText("Puntuación npc: " + puntuarOrdenador());

                //Comprobamos si la máquina tiene una puntuación superior a 21

                if (puntuarOrdenador() > 21) {

                    //Es superior a 21 (Victoria)

                    System.out.println("VICTORIA");
                    desenlace.setText("VICTORIA");
                    desenlace.setTextFill(Color.LIGHTGREEN);

                    player.setPuntuacion(player.getPuntuacion() + 100);
                    saldo.setText("Saldo: " + player.getPuntuacion() + "€");

                } else {

                    //No es superior a 21 (Derrota)

                    System.out.println("DERROTA");
                    desenlace.setText("DERROTA");
                    desenlace.setTextFill(Color.RED);

                    player.setPuntuacion(player.getPuntuacion() - 100);
                    saldo.setText("Saldo: " + player.getPuntuacion() + "€");

                }
            }
        } else {

            //Tu puntuacion supera los 21 puntos (Derrota)

            System.out.println("DERROTA");
            desenlace.setText("DERROTA");
            desenlace.setTextFill(Color.RED);

            player.setPuntuacion(player.getPuntuacion() - 100);
            saldo.setText("Saldo: " + player.getPuntuacion() + "€");

        }
        opciones.getChildren().clear();
        opciones.getChildren().add(desenlace);

        terminarPartida();

        if (player.getPuntuacion() == 0) {
            mostrarOpciones();
        }
    }

    //Método que bloquea cualquier acción excepto volver a jugar tras terminar la partida

    private void terminarPartida() {

        opciones.setBackground(new Background(fondoJuego));

        //Se muestra los botones que permiten reiniciar la partida o salir

        Image imageRestart = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Misceláneo/restart.png")));
        ImageView iconoRestart = new ImageView();

        iconoRestart.setImage(imageRestart);
        iconoRestart.setPreserveRatio(true);
        iconoRestart.setFitHeight(40);

        opciones.getChildren().addAll(btnPlay, salir);

        salir.setGraphic(iconoRestart);
        salir.setText("Salir al menú");
        salir.setTextFill(Color.WHITE);
        salir.setFont(Font.font("Frank Ruehl CLM", 30));
        salir.setContentDisplay(ContentDisplay.RIGHT);
        salir.setBackground(null);
        salir.setOnMouseClicked(event -> guardarPuntuacion());
        salir.setBorder(new Border(bordeOut));
        salir.setOnMouseEntered(mouseEvent ->
                salir.setBorder(new Border(bordeIn))
        );
        salir.setOnMouseExited(mouseEvent ->
                salir.setBorder(new Border(bordeOut))
        );

        btnPlay.setText("Jugar de nuevo");
        btnPlay.setVisible(true);

        //Se borran todos los mazos y la baraja

        ordenador.clear();
        jugador.clear();

        //Se deshabilitan los botones de juego

        btnPedir.setDisable(true);
        btnParar.setDisable(true);

        if (player.getPuntuacion() <= 0) {
            mostrarOpciones();
            player = new Jugador();
        }
    }


    private void mostrarRanking() {
        try {

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("rankings.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Ranking Jugadores");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(btnRanking.getScene().getWindow());
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    private void guardarPuntuacion() {

        //Se crean los elementos para la apertura de la ventana de guardado de datos

        Usuario usuario = new Usuario();
        Stage registro = new Stage();
        Scene scene = new Scene(usuario);

        //Se abre la pantalla de guardado para establecer el nombre

        usuario.establecerPuntuacion(player.getPuntuacion());
        registro.setScene(scene);
        registro.initStyle(StageStyle.TRANSPARENT);
        registro.show();

        //Si se presiona en "Guardar", el nombre no está vacio, se crea un registro con ese nombre y la puntiacion obtenida. Despues se cierra la ventana de guardado y vuelve al menu inicial

        usuario.obtenerBotonGuardar().setOnAction(mouseEvent -> {
            if (!usuario.obtenerNombre().isEmpty()) {


                player.setNombre(usuario.obtenerNombre());

                //Se guarda el registro y luego se cierra la venta de guardado

                try {
                    URL resource = getClass().getResource("ranking.csv");
                    File archivo = new File(resource.toURI());
                    FileWriter fileWriter = new FileWriter(archivo, true);

                    fileWriter.write(player.toString() + "\n");
                    fileWriter.close();

                    mostrarOpciones();

                    registro.hide();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                player = new Jugador();
            }
        });

        //Si se presiona en "Salir sin guardar", cierra la ventana de guardado y vuelve al menu inicial  sin guardar

        usuario.obtenerBotonSalir().setOnAction(mouseEvent -> {
                    mostrarOpciones();
                    registro.hide();
                    player = new Jugador();
                }
        );

    }

    //Método para voltear la primera carta de lá máquina
    private void mostrarCartaOrdenador() {
        ImageView im = (ImageView) ordenadorPov.getChildren().get(0);
        Carta c = ordenador.get(0);
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("cartas/" + c.getImagen() + ".png")));
        im.setImage(img);
    }

    //Método para crear una baraja
    public void crearBaraja() {
        char[] palos = {'C', 'T', 'P', 'D'};
        String[] nombres = {"AS", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        int valor;

        for (char palo : palos) {
            for (int i = 0; i < 13; i++) {
                valor = (i >= 10) ? 10 : i + 1;
                Carta carta = new Carta(palo, nombres[i], valor);
                this.baraja.add(carta);
            }
        }
    }

    //Genera una carta aleatoria para ser entregada
    public Carta sacarCarta() {
        Carta carta = null;
        Random aleatorio = new Random(System.currentTimeMillis());
        boolean control = true;
        while (control) {
            carta = this.baraja.get(aleatorio.nextInt(52));
            if (!carta.isRepartida()) {
                carta.setRepartida(true);
                control = false;
            }
        }

        return carta;
    }

    //Método para repartir una carta al jugador
    public void cartaJugador() {
        this.jugador.add(this.sacarCarta());
        Carta ultimaCarta = this.jugador.get(this.jugador.size() - 1);
        String cartaSacada = ultimaCarta.getImagen();

        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("cartas/" + cartaSacada + ".png")));
        ImageView image = new ImageView();

        image.setImage(img);
        image.setPreserveRatio(true);
        image.setFitHeight(200);
        image.setLayoutX(60 * (this.jugador.size() - 1));
        image.setEffect(new DropShadow(20, Color.BLACK));
        jugadorPov.getChildren().add(image);

        //Si el jugador recibe un as pasa esto...

        if (ultimaCarta.getValor() == 1) {

            //Comprobamos que la puntuacion se pasa de 21 al sumar 11

            if (puntuarJugador() + 10 <= 21) {

                //Si la puntuacion no se pasa de 21 al sumar 11, se cambia el valor a 11

                ultimaCarta.setValor(11);

            }

            //Si la puntuacion se pasa de 21 al sumar 11 no hacemos nada :)

        }

        puntuarJugador();

        //Si la puntuación del jugador supera los 20 puntos, termina el turno automáticamente
        if (puntuarJugador() >= 21) {
            terminarTurno();
        }

    }

    //Método para repartir una carta al ordenador
    public void cartaOrdenador() {
        this.ordenador.add(this.sacarCarta());
        Carta ultimaCarta = this.ordenador.get(this.ordenador.size() - 1);
        String cartaSacada;

        //Pone la primera carta de la máquina boca abajo
        if (this.ordenador.size() == 1) {
            cartaSacada = "Trasera";
        } else {
            cartaSacada = ultimaCarta.getImagen();
        }

        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("cartas/" + cartaSacada + ".png")));
        ImageView image = new ImageView();

        image.setImage(img);
        image.setPreserveRatio(true);
        image.setFitHeight(200);
        image.setLayoutX(60 * (this.ordenador.size() - 1));
        image.setEffect(new DropShadow(20, Color.BLACK));

        ordenadorPov.getChildren().add(image);

        //Si el ordenador recibe un as pasa esto...

        if (ultimaCarta.getValor() == 1) {

            //Comprobamos que la puntuacion se pasa de 21 al sumar 11

            if (puntuarJugador() + 10 <= 21) {

                //Si la puntuacion no se pasa de 21 al sumar 11, se cambia el valor a 11

                ultimaCarta.setValor(11);

            }

            //Si la puntuacion se pasa de 21 al sumar 11 no hacemos nada :)

        }
    }

    //Establece la puntuación de la máquina con la suma de los valores de las cartas
    public int puntuarOrdenador() {
        AtomicInteger suma = new AtomicInteger();
        this.ordenador.forEach(carta -> {
            suma.addAndGet(carta.getValor());
        });
        return suma.intValue();
    }


    //Establece la puntuación del jugador con la suma de los valores de las cartas
    public int puntuarJugador() {
        AtomicInteger suma = new AtomicInteger();
        this.jugador.forEach(carta -> {
            suma.addAndGet(carta.getValor());
        });
        tuPuntuacion.setText("Tu puntuación: " + suma);
        return suma.intValue();
    }

}