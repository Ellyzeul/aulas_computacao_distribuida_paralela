import axios from 'axios'
import { useEffect, useState } from 'react'
import ReactMarkdown from 'react-markdown'
import { URLMaker } from '../../requests'
import './NotesDisplay.css'
import { ClassDescription } from './types'


export const NotesDisplay = () => {
    const [classes, setClasses] = useState([] as ClassDescription[])
    const [id, setId] = useState(1)
    const incrementId = () => setId(id + 1)
    const [presentation, setPresentation] = useState("")

    useEffect(() => {
        axios.get(URLMaker('site', 'aulas.txt'))
            .then(response => {
                const data = response.data
                const rawClasses = data.split("\n")

                rawClasses.forEach((name: string) => {
                    setClasses([...classes, {
                        id: id,
                        name: name
                    }])
                    incrementId()
                })
            })

        axios.get(URLMaker('main', 'README.md'))
            .then(response => setPresentation(response.data))
    }, [])

    console.log(classes)
    console.log(presentation)

    return (
        <div className="notes_display">
            <ReactMarkdown>{presentation}</ReactMarkdown>
        </div>
    )
}