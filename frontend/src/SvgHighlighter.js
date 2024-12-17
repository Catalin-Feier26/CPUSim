import React, { useEffect } from 'react';

const SvgHighlighter = ({ svgRef, activeComponents }) => {
    useEffect(() => {
        if (svgRef.current) {
            activeComponents.forEach(({ id, color }) => {
                const element = svgRef.current.querySelector(`#${id}`);
                if (element) {
                    element.style.stroke = color;
                }
            });
        }
    }, [svgRef, activeComponents]);

    return null;
};

export default SvgHighlighter;
