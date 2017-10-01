import java.util.Iterator;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

/**
 * The Pokedex maintains the list of Pokemon species that have been encountered by the trainer.
 * It also maintains the trainer's inventory of Pokemon belonging to a particular species
 */
public class Pokedex {
	private ArrayList<PokemonSpecies> pokedex;

	/**
	 * Create a new, empty Pokedex
	 */
	public Pokedex() {
		pokedex = new ArrayList<PokemonSpecies>();
	}

	/**
	 * Load Pokedex from a file and update the Pokedex object's private pokedex.
	 * <p>
	 * This function does not provide de-duplication (if Gengar is already in your Pokedex and you loadFromFile 
	 * a Pokedex that also has a Gengar, you will have two entries of Gengar in your Pokedex).
	 * An error is thrown to prevent duplicate entries.
	 *  
	 * @param filepath the Pokedex to load; the file is expected to have lines delimited by white space with the
	 * following fields: 
	 *   <Pokedex Number> <Species Name> <Candies> [<cp1>, <cp2>, ...]
	 * for example:
	 *   94 gengar 3 2289
	 *   110 weezing 6 457 148
	 * @throws PokedexException if the pokedex has already been loaded (pokedex is not empty) with message Config.MULTIPLE_POKEDEX_EXCEPTION
	 * @throws FileNotFoundException if the file in @filepath@ cannot be found
	 */
	public void loadFromFile(String filepath) throws FileNotFoundException, PokedexException {
		// TODO
		File file=new File(filepath);
		Scanner in=new Scanner(file);
		if(file.exists()){
			while(in.hasNextLine()) {
				String line = in.nextLine();
				line.trim();
				String[] tokens = line.split("\\s+");
				int pokeNum=Integer.parseInt(tokens[0]);
				String name = tokens[1];
				name = name.toLowerCase(); // to make sure all cases are acceptable
				
				int candyNum=Integer.parseInt(tokens[2]);
				PokemonSpecies pokemonSpecie=new PokemonSpecies(pokeNum,
						name,candyNum);
				int a=tokens.length-3;
				int[] combatNum=new int[a];
				for(int j=3;j<tokens.length;j++){
					combatNum[j-3]=Integer.parseInt(tokens[j]);
					pokemonSpecie.loadPokemon(new Pokemon(pokeNum,name,combatNum[j-3]));
				}
				try{
					if(findSeenSpeciesData(pokemonSpecie.getSpeciesName()).
							equals(pokemonSpecie)){
						throw new PokedexException(Config.MULTIPLE_POKEDEX_EXCEPTION);
					}
				}catch (PokedexException e) {
					addNewSpecies(pokemonSpecie);
				}

			}
			in.close();
		}

		else throw new FileNotFoundException();

	}

	/**
	 * Create a record of a new Pokemon species in the Pokedex; used by PokemonTrainer when
	 * the player encounters a new Pokemon and that Pokemon escapes. This method is also
	 * used by other Pokedex methods such as addNewPokemon to make record of any 
	 * encountered Pokemon (whether that Pokemon is captured or escapes)
	 *
	 * @param species the PokemonSpecies that has been encountered
	 */
	public void addNewSpecies(PokemonSpecies species) {
		// TODO
		pokedex.add(species);
	}

	/**
	 * Add a new Pokemon to the player's inventory; may add a new species to the Pokedex
	 * if the Pokemon being added has not been encountered before. Used by PokemonTrainer 
	 * when the player encounters and catches a Pokemon.
	 *
	 * @param pokemon the new Pokemon that has been caught
	 */
	public void addNewPokemon(Pokemon pokemon) {
		// TODO
		int num=0; 
		boolean encountered=false;
		for(int i=0;i<pokedex.size();i++){
			if(pokedex.get(i).getSpeciesName().equals(pokemon.getSpecies())){
				encountered=true;
				num=i;
			}
		}
		if(encountered=false){
			PokemonSpecies newPokemon=new PokemonSpecies(pokedex.size()+1,pokemon.getSpecies(),0);
			addNewSpecies(newPokemon);
			pokedex.get(pokedex.size()-1).addNewPokemon(pokemon);
		}
		else{
			pokedex.get(num).addNewPokemon(pokemon);
		}
		/*for(int i=0;i<pokedex.size();i++){
			if(pokedex.get(i).getSpeciesName().equals(pokemon.getSpecies())){
				PokemonSpecies newPokemon=new PokemonSpecies(pokedex.size()+1,pokemon.getSpecies(),0);
				addNewSpecies(newPokemon);
				pokedex.get(pokedex.size()-1).addNewPokemon(pokemon);
			}
			else{
				pokedex.get(i).addNewPokemon(pokemon);
				}
		}*/

	}
	/**
	 * Transfer a Pokemon to the Professor. The Professor thanks the player by providing a candy
	 * associated with the Pokemon species of the Pokemon that was transferred.
	 *
	 * @param speciesName the species of the {@link Pokemon} to transfer
	 * @param cp the combatPower of the {@link Pokemon} to transfer
	 * @return the pokemon that was tranferred
	 * @throws PokedexException when either the Pokemon species given by the species name has not
	 * yet been encountered by the player or if there is no Pokemon with the combatPower given in cp
	 */
	public Pokemon transferPokemon(String speciesName, int cp) throws PokedexException {
		// TODO


		for(int i=0;i<pokedex.size();i++){
			if(pokedex.get(i).getSpeciesName().equals(speciesName)){
				return findSeenSpeciesData(speciesName).transferPokemon(cp);
			}
		} 
		throw new PokedexException(String.format(Config.UNCAUGHT_POKEMON,speciesName));
	}

	/**
	 * Lookup a species in the Pokedex; if it has been seen before, return its data; 
	 * otherwise, throw PokedexException.
	 *
	 * @param name the species name to lookup in the Pokedex
	 * @return the PokemonSpecies with speciesName given by name
	 * @throws PokedexException if the PokemonSpecies cannot be found (the player has not 
	 * yet encountered this species)
	 */
	public PokemonSpecies findSeenSpeciesData(String name) 
			throws PokedexException {
		// TODO
		for(int i=0;i<pokedex.size();i++){
			if(pokedex.get(i).getSpeciesName().equals(name)){
				return pokedex.get(i);
			}
		}
		throw new PokedexException(Config.POKEMON_NOT_FOUND);
	}

	/**
	 * While {@link findSeenSpeciesData} returns information for all Pokemon observed (and 
	 * caught), this function only returns species data for Pokemon that the Trainer has in 
	 * his or her inventory.
	 *
	 * @param speciesName the name of the species to find in the Pokedex
	 * @throws PokedexException [Config.UNSEEN_POKEMON] if the species has not been encountered or [Config.UNCAUGHT_POKEMON] if the player
	 * does not have any Pokemon of that species in his or her inventory
	 */
	public PokemonSpecies findCaughtSpeciesData(String speciesName) throws PokedexException {
		// TODO
		int num=0;
		for(int i=0;i<pokedex.size();i++){
			PokemonSpecies temp=pokedex.get(i);
			if(temp.getSpeciesName().equals(speciesName)){
				if(pokedex.get(i).isEmpty()==false ){
					num=i;
					return pokedex.get(num);
				}
				else{
					throw new PokedexException(String.format(Config.UNCAUGHT_POKEMON, speciesName));
				}
			}
			
		}
		throw new PokedexException(String.format(Config.UNSEEN_POKEMON, speciesName));

	}

	/**
	 * Create a String with the encountered Pokemon in the form:
	 * Bulbasaur
	 * Charmander
	 * ...
	 * where Pokemon species are listed in the order they have been encountered
	 *
	 * @return the String as described above
	 */
	public String seenPokemonMenu() {
		// TODO
		String list=new String("");
		for(int i=0;i<pokedex.size();i++){	        	
			list = list.concat(pokedex.get(i).getSpeciesName()+"\n");
		}

		return list;
	}

	/**
	 * While {@link seenPokemonMenu} returns a String with all Pokemon in the Pokedex (even ones that the
	 * trainer failed to capture), this function returns a String containing only Pokemon current in 
	 * the trainer's inventory
	 *
	 * @return the String as described above
	 */
	public String caughtPokemonMenu() {
		// TODO

		String list=new String("");
		for(int i=0;i<pokedex.size();i++){
			PokemonSpecies temp=pokedex.get(i);
			if(temp.isEmpty()==false){
				list =list.concat(temp.getSpeciesName()+"\n");
			}	
		}
		return list;
	}

	/**
	 * Serialize Pokedex into a String for a save file. The file format is described
	 * in {@link loadFromFile}
	 * 
	 * @return the complete Pokedex in String form, ready to be written to a file
	 */
	public String toString() {
		// TODO
		String list=new String("");
		Iterator<PokemonSpecies>str=pokedex.iterator();
		while(str.hasNext()){
			PokemonSpecies temp=str.next();
			list =list.concat(String.valueOf(temp.getPokedexNumber())+" ");
			list =list.concat(temp.getSpeciesName()+" ");
			list =list.concat(String.valueOf(temp.getCandies())+" ");
			list =list.concat(String.valueOf(temp.caughtPokemonToString())+"\n");
		}

		return list;
	}
}
