import java.util.Scanner;

public class RuletaRusa {

    public static void menuString(){
        System.out.println("Elija accion: "
                + "\n\t1: Pasar"
                + "\n\t2: Disparar"
                + "\n\t3: Girar el cilindro");
    }
    
    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Bienvenido a la RuletaRusa."
                + "\nVa a jugar contra un oponente controlado por la maquina."
                + "\nVan a usar un solo revolver cargado con UNA BALA. "
                + "La posicion de la bala será aleatoria.\n"
                + "\nSe apuestan 100 Rupias cada uno por RONDA."
                + "\nSe van a pasar el revolver por turnos (empezando por el jugador HUMANO)"
                + "\nhasta que uno de los dos pase o muera."
                + "\nEn caso de pasar, el contrincante gana la apuesta y comienza una nueva ronda."
                + "\nSiempre puede girar el cilindro para añadir aleatoriedad"
                + "\n¡SUERTE!");
        
        //Se elige DIFICULTAD
        int dificultad = 0;
        while (dificultad < 1 || dificultad > 3) {
            System.out.println("\nSeleccione dificultad: "
                + "\n\t1: Facil"
                + "\n\t2: Media"
                + "\n\t3: Dificil");
            dificultad = scan.nextInt();
        }
        
        //Se crean REVOLVER, IA
        Revolver r = new Revolver();
        IA ia = new IA (dificultad, r);
        
        //Se crea JUGADOR
        System.out.println("Introduzca nombre de jugador:");
        String nombre = scan.next();
        while (nombre.equals("Vladimir")) {
            System.out.println("Nombre no valido, elije otro");
            nombre = scan.next();
        }
        Jugador j = new Jugador (nombre, r);
        
        
        boolean seguirJuego = true;
        while (seguirJuego) {
            
            System.out.println("\n--------------------------"
                    + "\nDinero " + j.NOMBRE + ": " + j.getDinero() +
                    "\nDinero " + IA.NOMBRE + ": " + ia.getDinero()+
                    "\n--------------------------"
                    + "\nAmbos apostais 100 Rupias, y el cilindro gira");
            
            r.girarCilindro();
            
            boolean noPasa = true;
            boolean noPasaIA = true;
            
            boolean seguirRonda = true;
            while (seguirRonda) {
                
                System.out.println("\nTurno de " + j.NOMBRE);
                
                
                int eleccion = 0;
                while (eleccion == 0) {
                    while (eleccion < 1 || eleccion > 3 ) {
                        menuString(); 
                        eleccion = scan.nextInt();
                    }
                    switch (eleccion) {
                        case 1:
                            noPasa = j.pasar();
                            break;
                        case 2:
                            j.dispararse();
                            break;
                        case 3:
                            r.girarCilindro();
                            eleccion = 0;
                            break;
                        default:
                            System.out.println("Eleccion incorrecta");;
                    } 
                }
                
                //Turno de IA, depende de si jugador pasa o muere
                if (j.isVivo() && noPasa) {
                    System.out.println("\nTurno de " + IA.NOMBRE);
                    noPasaIA = ia.hacerTrampas();
                }
                
                //Si alguno pasa el otro gana la apuesta
                if (!noPasa) {
                    ia.ganar();
                }else if(!noPasaIA){
                    j.ganar();
                }
                //La ronda sigue si ambos siguen vivos y no han pasado
                seguirRonda = ia.isVivo() && j.isVivo() && noPasa && noPasaIA;
            }
            //El juego continúa si ambos están vivos y tienen dinero suficiente
            seguirJuego = ia.isVivo() && j.isVivo() && ia.getDinero() >= 100 && j.getDinero() >= 100;
        }
        
        //Mensaje final
        System.out.println("\n ---FIN---");
        if (j.isVivo()) {
            if (j.getDinero() > 0) {
                System.out.println("Has ganado " + j.getDinero() +
                        " Rupias, y mas importante, sigues VIVO");
            }else
                System.out.println("Te has arruinado, pero aun sigues VIVO");
        }else{
            System.out.println("Lo sentimos, has MUERTO");
        }
    }
}
