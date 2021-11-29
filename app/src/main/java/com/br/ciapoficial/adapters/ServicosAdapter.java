package com.br.ciapoficial.adapters;

//public class ServicosAdapter extends RecyclerView.Adapter<ServicosAdapter.MyViewHolder> {
//
////    private List<Servico> servicos;
////    private Context context;
////
////    public ServicosAdapter(List<Servico> lista, Context c) {
////        this.servicos = lista;
////        this.context = c;
////    }
////
////    @NonNull
////    @Override
////    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_atendimentos, parent, false);
////        return new MyViewHolder(itemLista);
////    }
////
////    @Override
////    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
////
////        Servico servico = servicos.get(position);
////
////        if(!servico.getOficiaisResponsaveis().isEmpty() && servico.getOficiaisResponsaveis() != null) {
////            String resumoOficiais = servico.getOficiaisResponsaveis().get(0).
////                    replace("[", "").replace("]", "");
////
////            if(servico.getOficiaisResponsaveis().size() > 1)
////            {
////                holder.nomeOficiais.setText(resumoOficiais + "...+(" + (servico.getOficiaisResponsaveis().size() - 1) + ")");
////            }else
////            {
////                holder.nomeOficiais.setText(servico.getOficiaisResponsaveis().toString().
////                        replace("[", "").replace("]", ""));
////            }
////        }
////
////        if(!servico.getAtendidos().isEmpty() && servico.getOficiaisResponsaveis() != null) {
////
////            String resumoAtendidos = servico.getAtendidos().
////                    get(0).replace("[", "").
////                    replace("]", "");
////
////            if(servico.getAtendidos().size() > 1)
////            {
////                holder.nomeAtendidos.setText(resumoAtendidos + "...+(" + (servico.getAtendidos().size() - 1) + ")");
////            }else
////            {
////                holder.nomeAtendidos.setText(servico.getAtendidos().toString().
////                        replace("[", "").replace("]", ""));
////            }
////
////        }
////
////
////
////        holder.data.setText(servico.getData());
////
////    }
////
////    @Override
////    public int getItemCount() {
////        return servicos.size();
////    }
////
////    public class MyViewHolder extends RecyclerView.ViewHolder {
////
////        TextView data, nomeAtendidos, nomeOficiais;
////
////        public MyViewHolder(View itemView) {
////            super(itemView);
////
////            data = itemView.findViewById(R.id.txtData);
////            nomeAtendidos = itemView.findViewById(R.id.txtNomeAtendido);
////            nomeOficiais = itemView.findViewById(R.id.txtNomeOficial);
////        }
////    }
//}
