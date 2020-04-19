package com.andreymasiero.agenda;

import java.io.IOException;
import java.time.LocalDate;

import com.andreymasiero.agenda.gui.EditaPessoaController;
import com.andreymasiero.agenda.gui.PessoaController;
import com.andreymasiero.agenda.model.Pessoa;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;
	private BorderPane principal;

	private ObservableList<Pessoa> dados = FXCollections.observableArrayList();

	public Main() {
		dados.add(new Pessoa("Andrey", "Masiero", LocalDate.of(1983, 10, 29)));
		dados.add(new Pessoa("Nelson", "Mandela", LocalDate.of(1918, 7, 18))); // 18 de julho de 1918
		dados.add(new Pessoa("Frida", "Kahlo", LocalDate.of(1907, 7, 6))); // 6 de julho de 1907
		dados.add(new Pessoa("Ada", "Lovelace", LocalDate.of(1815, 12, 10))); // 10 de dezembro de 1815
		dados.add(new Pessoa("Karen", "Horney", LocalDate.of(1885, 9, 16))); // 16 de setembro de 1885
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AgendApp");

		initMainStage();
		carregarTela();

	}

	private void carregarTela() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("gui/principal.fxml"));
			AnchorPane pessoasView = (AnchorPane) loader.load();

			this.principal.setCenter(pessoasView);

			PessoaController controller = loader.getController();
			controller.setMain(this);

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	private void initMainStage() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("gui/container.fxml"));
			this.principal = (BorderPane) loader.load();

			Scene cena = new Scene(this.principal);
			this.primaryStage.setScene(cena);
			this.primaryStage.show();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public boolean mostrarContatoDialog(Pessoa pessoa) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("gui/edita.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edita Pessoa");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(this.primaryStage);
			
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			EditaPessoaController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPessoa(pessoa);
			
			dialogStage.showAndWait();
			
			return controller.isOkClicked();
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public ObservableList<Pessoa> getDados() {
		return this.dados;
	}
}
