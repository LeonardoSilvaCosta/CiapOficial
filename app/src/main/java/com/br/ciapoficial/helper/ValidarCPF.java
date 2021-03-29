package com.br.ciapoficial.helper;

public class ValidarCPF {

        public static boolean validarCPF(String cpf) {

            boolean resultado;

            if(cpf.equals("000.000.000-00") ||
               cpf.equals("111.111.111-11") ||
               cpf.equals("222.222.222-22") ||
               cpf.equals("333.333.333-33") ||
               cpf.equals("444.444.444-44") ||
               cpf.equals("555.555.555-55") ||
               cpf.equals("666.666.666-66") ||
               cpf.equals("777.777.777-77") ||
               cpf.equals("888.888.888-88") ||
               cpf.equals("999.999.999-99") ||
               cpf.length() != 14) {

                resultado = false;

            }else{

                String s1, s2, s3, s4, s5, s6, s7, s8, s9, confere;
                int n1, n2, n3, n4, n5, n6, n7, n8, n9, verificador1, verificador2;

                s1 = cpf.substring(0, 1);
                s2 = cpf.substring(1, 2);
                s3 = cpf.substring(2, 3);
                s4 = cpf.substring(4, 5);
                s5 = cpf.substring(5, 6);
                s6 = cpf.substring(6, 7);
                s7 = cpf.substring(8, 9);
                s8 = cpf.substring(9, 10);
                s9 = cpf.substring(10, 11);

                n1 = Integer.parseInt(s1);
                n2 = Integer.parseInt(s2);
                n3 = Integer.parseInt(s3);
                n4 = Integer.parseInt(s4);
                n5 = Integer.parseInt(s5);
                n6 = Integer.parseInt(s6);
                n7 = Integer.parseInt(s7);
                n8 = Integer.parseInt(s8);
                n9 = Integer.parseInt(s9);

                verificador1 = (n1 * 10 + n2 * 9 + n3 * 8 + n4 * 7 + n5 * 6 + n6 * 5 + n7 * 4 + n8 * 3 + n9 * 2);
                if ((verificador1 % 11) < 2) {
                    verificador1 = 0;
                } else {
                    verificador1 = 11 - (verificador1 % 11);
                }

                verificador2 = (n1 * 11 + n2 * 10 + n3 * 9 + n4 * 8 + n5 * 7 + n6 * 6 + n7 * 5 + n8 * 4 + n9 * 3 + verificador1 * 2);
                if ((verificador2 % 11) < 2) {
                    verificador2 = 0;
                } else {
                    verificador2 = 11 - (verificador2 % 11);
                }

                confere = (s1 + s2 + s3 + "." + s4 + s5 + s6 + "." + s7 + s8 + s9 + "-" + verificador1 + verificador2);


                if (confere.equals(cpf)) {

                    resultado = true;
                } else {

                    resultado = false;
                }

            }

            return resultado;

        }
    }