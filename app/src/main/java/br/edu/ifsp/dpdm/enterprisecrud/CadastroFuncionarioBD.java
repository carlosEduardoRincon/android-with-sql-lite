package br.edu.ifsp.dpdm.enterprisecrud;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.dpdm.enterprisecrud.dao.FuncionarioDAO;
import br.edu.ifsp.dpdm.enterprisecrud.model.Funcionario;

public class CadastroFuncionarioBD extends Activity {

    private Funcionario f;
    private List<Funcionario> funcionarios;
    private FuncionarioDAO dao;
    private EditText edID;
    private EditText edNome;
    private EditText edIdade;
    private Spinner spSexo;
    private ListView lvFuncionarios;
    private String operacao;
    private String[] sexo = {"Masculino", "Feminino"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        edID = (EditText) findViewById(R.id.edID);
        edNome = (EditText) findViewById(R.id.edNome);
        edIdade = (EditText) findViewById(R.id.edIdade);
        spSexo = (Spinner) findViewById(R.id.spSexo);
        lvFuncionarios = (ListView) findViewById(R.id.lvFuncionarios);
        lvFuncionarios.setOnItemClickListener(selecionarFuncionario);
        lvFuncionarios.setOnItemLongClickListener(excluirFuncionario);
        funcionarios = new ArrayList<Funcionario>();
        operacao = new String("Novo");
        dao = new FuncionarioDAO(getApplicationContext());
        preencherSexo();
        atualizarLista();
    }

    private void excluirFuncionario(final int idFuncionario) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir funcionario?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Deseja excluir essa funcionario?")
                .setCancelable(false)
                .setPositiveButton(getString(R.string.sim),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (dao.deletar(idFuncionario)) {
                                    atualizarLista();
                                    exibirMensagem(getString(R.string.msgExclusao));
                                } else {
                                    exibirMensagem(getString(R.string.msgFalhaExclusao));
                                }

                            }
                        })
                .setNegativeButton(getString(R.string.nao),
                        (dialog, id) -> dialog.cancel());
        builder.create();
        builder.show();
    }

    private void preencherSexo() {
        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, sexo);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spSexo.setAdapter(adapter);
        } catch (Exception ex) {
            exibirMensagem("Erro: " + ex.getMessage());
        }
    }

    public void salvar(View v) {

        if (operacao.equalsIgnoreCase("Novo")) {
            f = new Funcionario();
        }

        f.setNome(edNome.getText().toString());

        f.setSexo(sexo[spSexo.getSelectedItemPosition()]
                .equalsIgnoreCase("Masculino") ? "M" : "F");
        f.setIdade(Integer.valueOf(edIdade.getText().toString()));

        if (operacao.equalsIgnoreCase("Novo")) {
            dao.salvar(f);
            exibirMensagem("Funcionário cadastrado com sucesso!");
        } else {
            dao.atualizar(f);
            exibirMensagem("Funcionário atualizado com sucesso!");
        }

        atualizarLista();
        limparDados();
    }

    public void novo(View v) {
        operacao = new String("Novo");
        limparDados();
    }

    private void limparDados() {
        edID.setText("");
        edNome.setText("");
        edNome.requestFocus();
        edIdade.setText("");
        spSexo.setSelection(0);
    }

    private void atualizarLista() {
        funcionarios = dao.listAll();
        if (funcionarios != null) {
            if (funcionarios.size() >= 0) {
                FuncionarioListAdapter pla = new FuncionarioListAdapter(
                        getApplicationContext(), funcionarios);
                lvFuncionarios.setAdapter(pla);
            }
        }
    }

    private AdapterView.OnItemClickListener selecionarFuncionario = new AdapterView.OnItemClickListener() {

        public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
            operacao = new String("Atualizar");
            f = funcionarios.get(pos);
            preecherDados(f);
        }
    };

    private AdapterView.OnItemLongClickListener excluirFuncionario = new AdapterView.OnItemLongClickListener() {
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
            excluirFuncionario(funcionarios.get(pos).getId());
            return true;
        }
    };

    private void preecherDados(Funcionario funcionario) {
        edID.setText(String.valueOf(funcionario.getId()));
        edNome.setText(funcionario.getNome());
        edIdade.setText(String.valueOf(funcionario.getIdade()));
        spSexo.setSelection(funcionario.getSexo().equalsIgnoreCase("M") ? 0 : 1);
    }

    private void exibirMensagem(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}
