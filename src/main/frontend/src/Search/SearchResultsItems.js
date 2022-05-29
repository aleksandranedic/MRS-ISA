import React, {useEffect, useState} from 'react'
import axios from "axios";
import {backLink, frontLink} from "../Consts";
import {SearchResultCard} from "./SearchResultCard";
import Pagination from "../Pagination";

const SearchResultsResources = ({list,name}) => {

    const [currentPage, setCurrentPage] = useState(1)

    const [postsPerPage] = useState(2)

    const indexOfLastVHPost = currentPage * postsPerPage;
    const indexOfFirstVHPost = indexOfLastVHPost - postsPerPage;
    const currentVHPost = list.slice(indexOfFirstVHPost, indexOfLastVHPost)

    const paginate = (number) => {
        setCurrentPage(number)
    }


    if (currentVHPost.length !== 0) {
        return <>{currentVHPost.map((item) =>
            (<SearchResultCard key={item.id} title={item.title} description={item.description}
                               link={frontLink +name+"/" + item.id}/>))}
                               {/*+ "adventure/"*/}
            <Pagination key={name} paginate={paginate} postPerPage={postsPerPage}
                        totalPosts={currentVHPost.length} name={name}/></>
    }
    return "";
}
export default SearchResultsResources