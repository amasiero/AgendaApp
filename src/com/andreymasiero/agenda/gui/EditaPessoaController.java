package com.andreymasiero.agenda.gui;

import com.andreymasiero.agenda.model.Pessoa;
import com.andreymasiero.agenda.util.DateUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditaPessoaController {

	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtSobrenome;
	@FXML
	private TextField txtDataNascimento;

	private Stage dialogStage;
	private Pessoa pessoa;
	private boolean okClicked = false;

	@FXML
	private void initialize() {
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;

		txtNome.setText(pessoa.getNome());
		txtSobrenome.setText(pessoa.getSobrenome());
		txtDataNascimento.setText(DateUtil.format(pessoa.getDataNascimento()));
	}

	public boolean isOkClicked() {
		return this.okClicked;
	}

	@FXML
	public void handleCancelar() {
		this.dialogStage.close();
	}

	@FXML
	public void handleSalvar() {
		if (ehValidaEntrada()) {
			pessoa.setNome(txtNome.getText());
			pessoa.setSobrenome(txtSobrenome.getText());
			pessoa.setDataNascimento(DateUtil.parse(txtDataNascimento.getText()));
			
			this.okClicked = true;
			dialogStage.close();
		}
	}

	private boolean ehValidaEntrada() {
		String mensagem = "";

		if (txtNome.getText() == null || txtNome.getText().isEmpty() || txtNome.getText().length() <= 3) {
			mensagem += "Nome inválido\n";
		}

		if (txtSobrenome.getText() == null || txtSobrenome.getText().isEmpty()
				|| txtSobrenome.getText().length() <= 3) {
			mensagem += "Sobrenome inválido\n";
		}

		if (txtDataNascimento.getText() == null || txtDataNascimento.getText().length() == 0) {
			mensagem += "Aniversário inválido!\n";
		} else {
			if (!DateUtil.validDate(txtDataNascimento.getText())) {
				mensagem += "Aniversário inválido. Use o formato dd/mm/yyyy!\n";
			}
		}

		if (mensagem.length() == 0) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Campos Inválidos");
			alert.setHeaderText("Por favor, corrija as informações.");
			alert.setContentText(mensagem);
			alert.showAndWait();

			return false;
		}

	}

}
