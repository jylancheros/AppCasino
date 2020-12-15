package co.edu.unab.tads.appcasino.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.edu.unab.tads.appcasino.R;
import co.edu.unab.tads.appcasino.databinding.ItemEmpleadoBinding;
import co.edu.unab.tads.appcasino.model.entity.Empleado;

public class EmpleadoAdapter extends RecyclerView.Adapter<EmpleadoAdapter.EmpleadoViewHolder> {

    private ArrayList<Empleado> empleados;
    private OnItemClickListener onItemClickListener;

    public EmpleadoAdapter(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
        onItemClickListener = null;
    }

    public void setEmpleados(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public EmpleadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEmpleadoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_empleado, parent, false);
        return new EmpleadoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpleadoViewHolder holder, int position) {
        Empleado empleado = empleados.get(position);
        holder.onBind(empleado);
    }

    @Override
    public int getItemCount() {
        return empleados.size();
    }

    public class EmpleadoViewHolder extends RecyclerView.ViewHolder {
        private ItemEmpleadoBinding binding;

        public EmpleadoViewHolder(@NonNull ItemEmpleadoBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        private void onBind(Empleado empleado) {
            binding.setEmpleado(empleado);
            if (onItemClickListener != null) {
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(empleado,getAdapterPosition());
                    }
                });
            }

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Empleado empleado, int position);
    }

}
