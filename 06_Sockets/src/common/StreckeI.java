package common;

public interface StreckeI
{


    public Boolean reserviere(Integer zugnummer, Boolean bRichtung, Integer n);
    public Boolean freigeben(Integer zugnummer, Boolean bRichtung, Integer n);

    public Integer getAbschnitt(Boolean bRichtung,Integer n);
    public Integer getStreckenLaenge();
    
    public String wechselnVon(Integer zugnummer, Boolean bRichtung, Integer from);
    public String wechselnNach(Integer zugnummer, Boolean bRichtung, Integer to);
    public String verlassen(Integer zugnummer, Boolean bRichtung);


    
//    public String ankommen(Integer zugnummer, Boolean bRichtung);
//    public void leaveAB(Integer zugnummer);

//  public void zeigeStreckenabschnitt();
//  public Integer getAnzahlZuege();

}