import { useEffect, useState, useCallback } from "react";
import axios from "axios";
import _ from "lodash";

export const usePokemonData = () => {
    const [loading, setLoading] = useState(false);
    const [pokemonList, setPokemonList] = useState([]);
    const [pokemonListDetails, setPokemonListDetails] = useState([]);
    const [_allPokemon, setAllPokemon] = useState([]);
    const [originalPokemonListDetails, setOriginalPokemonListDetails] = useState([]);
    const [searchQuery, setSearchQuery] = useState("");
    const [filters, setFilters] = useState({
        type: "",
        ability: "",
        weight: "",
        height: "",
        sortOrder: "",
    });

    // Fetch Pokémon list (first 40)
    useEffect(() => {
        const fetchPokemon = async () => {
            setLoading(true);
            try {
                const res = await axios.get(
                    `https://pokeapi.co/api/v2/pokemon?offset=0&limit=40`
                );
                setPokemonList(res.data.results);
            } catch (error) {
                console.error("Error fetching Pokémon list", error);
            } finally {
                setLoading(false);
            }
        };
        fetchPokemon();
    }, []);

    // Fetch all Pokémon (for search/filter)
    useEffect(() => {
        const fetchAllPokemon = async () => {
            try {
                const res = await axios.get(`https://pokeapi.co/api/v2/pokemon?limit=1118`);
                setAllPokemon(res.data.results);
            } catch (error) {
                console.error("Error fetching all Pokémon", error);
            }
        };
        fetchAllPokemon();
    }, []);

    // Fetch and transform Pokémon details
    useEffect(() => {
        if (pokemonList.length === 0) return;

        const fetchPokemonDetails = async () => {
            setLoading(true);
            try {
                const details = await Promise.all(
                    pokemonList.map(async (pokemon) => {
                        const res = await axios.get(pokemon.url);
                        const data = res.data;

                        // Extract base HP
                        const hpStat = data.stats.find(stat => stat.stat.name === "hp");
                        const baseHp = hpStat ? hpStat.base_stat : 100;

                        // Construct cry URL (official PokéAPI cry)
                        const cryUrl = `https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/latest/${data.id}.ogg`;

                        // Return battle-ready Pokémon object
                        return {
                            id: data.id,
                            name: data.name,
                            type: data.types[0]?.type.name || "normal",
                            level: 50, // Default level
                            hp: baseHp,
                            maxHp: baseHp,
                            food: "berries", // Custom field
                            sound: cryUrl, // Use cry as sound
                            owner: "Player1", // Default owner
                            cry: cryUrl,
                            sprites: data.sprites,
                            attacks: data.moves.slice(0, 4).map(m => m.move.name),
                        };
                    })
                );
                setPokemonListDetails(details);
                setOriginalPokemonListDetails(details);
            } catch (error) {
                console.error("Error fetching Pokémon details", error);
            } finally {
                setLoading(false);
            }
        };
        fetchPokemonDetails();
    }, [pokemonList]);

    // Filter Pokémon when filters or searchQuery change
    const filterPokemon = useCallback(() => {
        let filtered = originalPokemonListDetails;

        if (filters.type) {
            filtered = filtered.filter((p) =>
                p.type === filters.type
            );
        }
        if (filters.ability) {
            filtered = filtered.filter((p) =>
                p.abilities?.some((a) => a.ability.name === filters.ability)
            );
        }
        if (filters.weight) {
            filtered = filtered.filter((p) => p.weight >= filters.weight);
        }
        if (filters.height) {
            filtered = filtered.filter((p) => p.height >= filters.height);
        }
        if (searchQuery) {
            filtered = filtered.filter((p) =>
                p.name.toLowerCase().includes(searchQuery.toLowerCase())
            );
        }
        if (filters.sortOrder) {
            filtered = [...filtered].sort((a, b) =>
                filters.sortOrder === "asc"
                    ? a.name.localeCompare(b.name)
                    : b.name.localeCompare(a.name)
            );
        }

        setPokemonListDetails(filtered);
    }, [filters, searchQuery, originalPokemonListDetails]);

    useEffect(() => {
        filterPokemon();
    }, [filterPokemon]);

    // Debounced search handler
    const debouncedSearch = useCallback(
        _.debounce((value) => {
            setSearchQuery(value);
        }, 500),
        []
    );

    const handleSearchChange = (e) => {
        debouncedSearch(e.target.value);
    };

    // Filter change handler
    const handleFilterChange = (key, value) => {
        setFilters((prev) => ({ ...prev, [key]: value }));
    };

    return {
        loading,
        pokemonListDetails,
        handleSearchChange,
        handleFilterChange,
        filters,
    };
};


