<div style="text-align: center;">
  <h1>ScoreStripes</h1>
</div>

<p> ScoreStripes è un'applicazione enterprise per la vendita di prodotti sportivi, al momento disponibile come app android.</p>
<h2> Divisione del lavoro </h2>
<div style="text-align: center;">
    <img src="./readmeImages/Heatmap divisione lavoro.png" alt="Heatmap divisione lavoro" width="600">
</div>
<ul>
    <li> 
        <h3>Francesco Gallo</h3>
        Responsabile di tutto il server utilizzando il framework Spring Boot.<br>
        Fix di bug, testing e aggiunte nei client Android (es. token storage).<br>
        (Nel progetto si trova anche una cartella che contiene funzionalità tramite programmazione reattiva con Spring WebFlux e MongoDB, non completata e implementata con il resto per mancanza di tempo).<br>
        Tra le varie cose, si sottolineano:
        <ul>
            <li> <h4>Auditing</h4> </li>
            <li> <h4>Custom Rate limiting usando il paradigma AOP</h4> </li>
            <li> <h4>Custom Validators</h4> </li>
            <li> <h4>Documentazione API con Swagger    &nbsp&nbsp&nbsp&nbsp  <a href="https://github.com/Frxnesvo/Progetto-Enterprise-Application/blob/main/API%20Documentation.txt">txt</a> &nbsp&nbsp&nbsp&nbsp    <a href="https://localhost:8443/swagger-ui/index.html">html</a> </h4> </li>
            <li> <h4>Uso di HTTPS</h4> </li>
            <li> <h4>Uso di AWS S3 e presigned URL per lo storage delle immagini</h4> </li>
            <li> <h4>Auto DB cleaning per i token scaduti</h4> </li>
            <li> 
                <h4>Pagamenti tramite <a href="https://stripe.com/">Stripe</a></h4>
                <img src="./readmeImages/Stripe.jpeg" alt="Stripe" width="250">
            </li>
            <li> <h4>Invio mail con template</h4> </li>
        </ul>
    </li>
    <br>
    <li> 
        <h3>Francesco Cavaliere</h3>
        Responsabile della la logica del client Customer, login, register e logout in entrambi i client. Collaborazione nella parte di autorizzazione, autenticazione e nel client Admin.<br> 
        Sviluppato alcuni endpoint REST.<br> 
        Tra le varie cose, si sottolineano:
        <ul>
            <li> <h4>Gestione delle wishlist, carrello e viewmodels del client Admin</h4> </li>
        </ul>
    </li>
    <br>
    <li> 
        <h3>Aldo Gioia</h3>
        Responsabile del design e implementazione di ogni schermata per entrambi i client (UI/UX).<br>
        Gestione di quasi tutti i FormViewModels e di molti ViewModels nel client Admin.<br>
        Tra le varie cose si sottolineano:
        <ul>
            <li> <h4>Configurazione di Retrofit per le chiamate REST</h4> </li>
            <li> <h4>Configurazione di Moshi e MoshiCodeGen per la serializzazione e deserializzazione</h4> </li>
            <li> <h4>Uso del CompositionLocal per la gestione dei ViewModels</h4> </li>
        </ul>
    </li>
</ul>
