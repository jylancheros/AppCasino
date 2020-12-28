package co.edu.unab.tads.appcasino.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.edu.unab.tads.appcasino.R;
import co.edu.unab.tads.appcasino.databinding.ItemRegistroBinding;
import co.edu.unab.tads.appcasino.model.entity.Registro;

public class RegistroAdapter extends RecyclerView.Adapter<RegistroAdapter.RegistroViewHolder> {

    private ArrayList<Registro> registros;
    private OnItemClickListener onItemClickListener;

    public RegistroAdapter(ArrayList<Registro> registros) {
        this.registros = registros;
        onItemClickListener = null;
    }

    public void setRegistros(ArrayList<Registro> registros) {
        this.registros = registros;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RegistroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRegistroBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_registro, parent, false);
        return new RegistroViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RegistroViewHolder holder, int position) {
        Registro myRegister = registros.get(position);
        holder.onBind(myRegister);
    }

    @Override
    public int getItemCount() {
        return registros.size();
    }

    public class RegistroViewHolder extends RecyclerView.ViewHolder {
        private ItemRegistroBinding binding;

        public RegistroViewHolder(@NonNull ItemRegistroBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        private void onBind(Registro myRegister) {
            binding.setMyRegister(myRegister);
            if (onItemClickListener != null) {
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(myRegister,getAdapterPosition());
                    }
                });
            }

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Registro myRegister, int position);
    }

}
