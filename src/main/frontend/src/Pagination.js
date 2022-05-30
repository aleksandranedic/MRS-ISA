import React from 'react'

const Pagination = ({postPerPage, totalPosts, paginate,name}) => {
    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(totalPosts / postPerPage); i++) {
        pageNumbers.push(i);
    }
    return (
        <nav>
            <ul className="pagination justify-content-center">
                {pageNumbers.map(number =>
                    (<li key={name+number} className="page-link">
                        <a onClick={() => paginate(number)} href="!#" className="page-link">{number}</a>
                    </li>)
                )}
            </ul>
        </nav>
    )
}
export default Pagination