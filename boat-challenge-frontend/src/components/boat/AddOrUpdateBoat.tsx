import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { addBoat, updateBoat, fetchBoatById } from '../../services/boat';
import { BoatContent } from '../../types/boat';

const AddOrUpdateBoat: React.FC = () => {
    const { id } = useParams<{ id: string }>(); // Get the boat id from the URL (if available)
    const [boatContent, setBoatContent] = useState<BoatContent>({
        name: '',
        description: '',
    });
    const [isEditMode, setIsEditMode] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [successMessage, setSuccessMessage] = useState<string | null>(null);
    const navigate = useNavigate();

    // Fetch the boat details if we're in edit mode (i.e., if `id` exists)
    useEffect(() => {
        if (id) {
            setIsEditMode(true); // Set the component to edit mode
            const fetchBoatDetails = async () => {
                try {
                    const boat = await fetchBoatById(parseInt(id));
                    setBoatContent({ name: boat.name, description: boat.description });
                } catch (err) {
                    setError('Failed to fetch boat details.');
                }
            };
            fetchBoatDetails();
        } else {
            setIsEditMode(false); // Set the component to add mode if no `id` is provided
        }
    }, [id]);

    // Handle form input changes
    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setBoatContent({
            ...boatContent,
            [name]: value,
        });
    };

    // Handle form submission
    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault(); // Prevent page reload

        try {
            if (isEditMode && id) {
                await updateBoat(parseInt(id), boatContent); // Update boat if in edit mode
                setSuccessMessage('Boat updated successfully!');
            } else {
                await addBoat(boatContent); // Add a new boat if in add mode
                setSuccessMessage('Boat added successfully!');
            }

            setTimeout(() => {
                navigate('/boats'); // Navigate back to boat list after a delay
            }, 2000);
        } catch (err) {
            setError('Failed to save the boat. Please try again.');
        }
    };

    return (
        <div>
            <h1>{isEditMode ? 'Update Boat' : 'Add New Boat'}</h1>

            {/* Display success or error message */}
            {successMessage && <p style={{ color: 'green' }}>{successMessage}</p>}
            {error && <p style={{ color: 'red' }}>{error}</p>}

            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="name">Boat Name:</label>
                    <input
                        type="text"
                        id="name"
                        name="name"
                        value={boatContent.name}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="description">Description:</label>
                    <textarea
                        id="description"
                        name="description"
                        value={boatContent.description}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <button type="submit">{isEditMode ? 'Update Boat' : 'Add Boat'}</button>
            </form>
        </div>
    );
};

export default AddOrUpdateBoat;