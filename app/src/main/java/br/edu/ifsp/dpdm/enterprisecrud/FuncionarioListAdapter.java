package br.edu.ifsp.dpdm.enterprisecrud;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsp.dpdm.enterprisecrud.model.Funcionario;

public class FuncionarioListAdapter extends BaseAdapter {

    private Context context;
    private List<Funcionario> lista;

    public FuncionarioListAdapter(Context context, List<Funcionario> lista) {
        this.context = context;
        this.lista = lista;
    }

    public int getCount() {
        return lista.size();
    }

    public Object getItem(int position) {
        return lista.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"SetTextI18n", "ViewHolder", "InflateParams"})
    public View getView(int position, View convertView, ViewGroup parent) {
        Funcionario f = lista.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.funcionarios, null);

        TextView id = (TextView) view.findViewById(R.id.txtIdFuncionario);
        id.setText("ID: " + f.getId());

        TextView nome = (TextView) view.findViewById(R.id.txtNomeFuncionario);
        nome.setText("Nome: " + f.getNome());

        TextView idade = (TextView) view.findViewById(R.id.txtIdadeFuncionario);
        idade.setText("Idade: " + f.getIdade());

        TextView sexo = (TextView) view.findViewById(R.id.txtSexo);
        sexo.setText("Sexo: " + f.getSexo());

        return view;
    }
}
